package com.nazam.todo_clean.data.mapper

import com.nazam.todo_clean.data.local.entity.TaskEntity
import com.nazam.todo_clean.domain.model.Priority
import com.nazam.todo_clean.domain.model.Task

/**
 * Convertit entre les modèles:
 * - Domain -> Data (Task -> TaskEntity)
 * - Data   -> Domain (TaskEntity -> Task)
 */
object TaskMapper {

    fun toEntity(task: Task): TaskEntity =
        TaskEntity(
            id = task.id,
            title = task.title,
            description = task.description,
            priority = task.priority.name, // enum -> String
            isDone = task.isDone,
            createdAt = task.createdAt
        )

    fun toDomain(entity: TaskEntity): Task =
        Task(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            priority = entity.priority.toPrioritySafe(),
            isDone = entity.isDone,
            createdAt = entity.createdAt
        )

    // Sécurise la conversion String -> enum (évite crash si valeur inconnue)
    private fun String.toPrioritySafe(): Priority =
        try { Priority.valueOf(this) } catch (_: IllegalArgumentException) { Priority.MEDIUM }
}