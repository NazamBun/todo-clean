package com.nazam.todo_clean.domain.usecase

import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.repository.TaskRepository
import javax.inject.Inject
/**
 * Use case : ajouter une tâche.
 * Simple et pur : il appelle juste le repository.
 */
class AddTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Long {
        return taskRepository.add(task)
    }
}
