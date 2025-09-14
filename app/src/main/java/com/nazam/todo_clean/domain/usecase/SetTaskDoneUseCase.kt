package com.nazam.todo_clean.domain.usecase

import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.repository.TaskRepository

class SetTaskDoneUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task, done: Boolean) {
        repository.update(task.copy(isDone = done))
    }
}