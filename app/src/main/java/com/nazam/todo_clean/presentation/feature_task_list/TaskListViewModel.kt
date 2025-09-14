package com.nazam.todo_clean.presentation.feature_task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.usecase.DeleteTaskUseCase
import com.nazam.todo_clean.domain.usecase.GetTasksUseCase
import com.nazam.todo_clean.domain.usecase.SearchTasksUseCase
import com.nazam.todo_clean.domain.usecase.SetTaskDoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de l'écran liste des tâches.
 * - Lis les tâches via GetTasksUseCase()
 * - Supprime une tâche via DeleteTaskUseCase(id)
 * - Expose un UiState simple pour l'écran
 */
@OptIn(FlowPreview::class)
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasks: GetTasksUseCase,
    private val searchTasks: SearchTasksUseCase,
    private val deleteTask: DeleteTaskUseCase,
    private val setTaskDone: SetTaskDoneUseCase
) : ViewModel() {

    private val query = MutableStateFlow("")

    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        // Écoute la query et alimente la liste
        viewModelScope.launch {
            query
                .debounce(300)                // petit délai pour éviter spam
                .distinctUntilChanged()
                .flatMapLatest { q ->
                    if (q.isBlank()) getTasks() else searchTasks(q)
                }
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
                .collect { tasks ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            tasks = tasks,
                            error = null
                        )
                    }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun onDeleteClicked(id: Int) {
        viewModelScope.launch {
            try {
                deleteTask(id)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Delete failed") }
            }
        }
    }

    fun onDoneChanged(task: Task, done: Boolean) {
        viewModelScope.launch {
            try {
                setTaskDone(task, done)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Update failed") }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class TaskListUiState(
    val isLoading: Boolean = true,
    val tasks: List<Task> = emptyList(),
    val error: String? = null
)