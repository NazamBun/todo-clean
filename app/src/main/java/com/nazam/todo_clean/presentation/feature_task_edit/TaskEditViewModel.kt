package com.nazam.todo_clean.presentation.feature_task_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazam.todo_clean.domain.model.Priority
import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.usecase.AddTaskUseCase
import com.nazam.todo_clean.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour l'écran d'édition/création de tâche.
 * - Si taskId == -1 -> création
 * - Sinon -> mise à jour
 */
@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val addTask: AddTaskUseCase,
    private val updateTask: UpdateTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskEditUiState())
    val uiState: StateFlow<TaskEditUiState> = _uiState

    /**
     * Sauvegarde une tâche (création ou update).
     */
    fun saveTask(
        taskId: Int?,
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                if (taskId == null || taskId == -1) {
                    // Création
                    addTask(
                        Task(
                            id = 0, // sera auto-généré par Room
                            title = title,
                            description = description,
                            priority = priority,
                            isDone = false
                        )
                    )
                } else {
                    // Mise à jour
                    updateTask(
                        Task(
                            id = taskId,
                            title = title,
                            description = description,
                            priority = priority,
                            isDone = false
                        )
                    )
                }

                _uiState.value = TaskEditUiState(success = true)
            } catch (e: Exception) {
                _uiState.value = TaskEditUiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun clearMessages() {
        _uiState.value = TaskEditUiState()
    }
}

/**
 * État de l’écran d’édition.
 */
data class TaskEditUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)