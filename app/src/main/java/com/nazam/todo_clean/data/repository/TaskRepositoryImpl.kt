package com.nazam.todo_clean.data.repository

import com.nazam.todo_clean.data.local.dao.TaskDao
import com.nazam.todo_clean.data.mapper.TaskMapper
import com.nazam.todo_clean.domain.model.Task
import com.nazam.todo_clean.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implémentation du dépôt de tâches.
 * - Utilise Room (TaskDao) pour la persistence.
 * - Utilise TaskMapper pour convertir Entity <-> Domain.
 */

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun observeTasks(): Flow<List<Task>> =
        taskDao.observeAll().map { list -> list.map(TaskMapper::toDomain) }

    override fun search(query: String): Flow<List<Task>> =
        taskDao.search(query).map { list -> list.map(TaskMapper::toDomain) }

    override suspend fun getById(id: Int): Task? =
        taskDao.getById(id)?.let(TaskMapper::toDomain)

    override suspend fun add(task: Task): Long =
        taskDao.insert(TaskMapper.toEntity(task))

    override suspend fun update(task: Task) {
        taskDao.update(TaskMapper.toEntity(task))
    }

    override suspend fun delete(id: Int) {
        taskDao.deleteById(id)
    }
}