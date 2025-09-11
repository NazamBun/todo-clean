package com.nazam.todo_clean.domain.usecase

import com.nazam.todo_clean.domain.repository.TaskRepository

/**
 * Use case : supprimer une tâche par son identifiant.
 * La logique passe uniquement par le repository (Clean Architecture).
 */
class DeleteTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.delete(id)
    }
}
