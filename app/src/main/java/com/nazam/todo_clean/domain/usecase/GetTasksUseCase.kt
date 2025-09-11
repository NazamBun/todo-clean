package com.nazam.todo_clean.domain.usecase

import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case : récupérer toutes les tâches.
 * Respecte SOLID :
 * - SRP : Une seule responsabilité (récupérer les tâches)
 * - DIP : Dépend d'une abstraction (TaskRepository interface)
 * - Pure : Pas de dépendance à des frameworks externes
 */
class GetTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> = repository.observeTasks()
}
