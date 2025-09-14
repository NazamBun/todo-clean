package com.nazam.todo_clean.presentation.feature_task_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.usecase.GetTaskByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * VM pour l’écran de détails.
 * - Récupère taskId depuis la nav (SavedStateHandle)
 * - Charge la tâche via GetTaskByIdUseCase
 */
@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val getTaskById: GetTaskByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailUiState(isLoading = true))
    val uiState: StateFlow<TaskDetailUiState> = _uiState

    private val taskId: Int = savedStateHandle.get<Int>("taskId") ?: -1

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = TaskDetailUiState(isLoading = true)
            try {
                val task = if (taskId >= 0) getTaskById(taskId) else null
                _uiState.value = TaskDetailUiState(task = task, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = TaskDetailUiState(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    fun retry() = load()
}

data class TaskDetailUiState(
    val isLoading: Boolean = false,
    val task: Task? = null,
    val error: String? = null
)