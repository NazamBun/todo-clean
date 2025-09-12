package com.nazam.todo_clean.domain.usecase

import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.repository.TaskRepository

/**
 * Use case : ajouter une tâche.
 * Simple et pur : il appelle juste le repository.
 */
class AddTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Long {
        return repository.add(task)
    }
}
