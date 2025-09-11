package com.nazam.todo_clean.domain.usecase

import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.repository.TaskRepository

/**
 * Use case : mettre à jour une tâche existante.
 * Il encapsule l'appel au repository pour respecter Clean Architecture.
 */
class UpdateTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        repository.update(task)
    }
}
