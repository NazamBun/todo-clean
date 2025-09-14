package com.nazam.todo_clean.domain.usecase

import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.repository.TaskRepository

class GetTaskByIdUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Task? = repository.getById(id)
}