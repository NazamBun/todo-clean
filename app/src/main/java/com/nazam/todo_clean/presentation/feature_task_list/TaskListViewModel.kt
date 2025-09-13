package com.nazam.todo_clean.presentation.feature_task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.usecase.DeleteTaskUseCase
import com.nazam.todo_clean.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de l'écran liste des tâches.
 * - Lis les tâches via GetTasksUseCase()
 * - Supprime une tâche via DeleteTaskUseCase(id)
 * - Expose un UiState simple pour l'écran
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasks: GetTasksUseCase,
    private val deleteTask: DeleteTaskUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            getTasks()
                .onEach { tasks ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        tasks = tasks,
                        error = null
                    )
                }
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )

                }
                .collect { /* handled in onEach */ }
        }
    }
    fun onDeleteClicked(id: Int) {
        viewModelScope.launch {
            try {
                deleteTask(id)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Delete failed"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

/** État simple pour la liste */
data class TaskListUiState(
    val isLoading: Boolean = true,
    val tasks: List<Task> = emptyList(),
    val error: String? = null
)