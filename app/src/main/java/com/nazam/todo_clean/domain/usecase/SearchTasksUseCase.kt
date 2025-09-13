package com.nazam.todo_clean.domain.usecase

import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class SearchTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(query: String): Flow<List<Task>> =
        repository.search(query)
}