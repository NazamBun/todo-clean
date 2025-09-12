package com.nazam.todo_clean.domain.repository

import com.nazam.todo_clean.domain.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Contrat du dépôt de tâches (pas d'implémentation ici).
 * La couche data fournira l'implémentation.
 */
interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    fun search(query: String): Flow<List<Task>>
    suspend fun getById(id: Int): Task?
    suspend fun add(task: Task): Long
    suspend fun update(task: Task)
    suspend fun delete(id: Int)
}
