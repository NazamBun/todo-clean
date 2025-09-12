package com.nazam.todo_clean.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nazam.todo_clean.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Flux de toutes les tâches, triées par date de création (plus récentes en premier)
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<TaskEntity>>

    // Une tâche par id
    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): TaskEntity?

    // Recherche simple par titre
    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun search(query: String): Flow<List<TaskEntity>>

    // Ajout → Room renvoie l'id (Long)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TaskEntity): Long

    // Mise à jour
    @Update
    suspend fun update(entity: TaskEntity)

    // Suppression par id
    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: Int)
}