package com.nazam.todo_clean.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nazam.todo_clean.data.local.dao.TaskDao
import com.nazam.todo_clean.data.local.entity.TaskEntity

/**
 * Base de données Room de l'application.
 * Version 1, pas d'export de schéma pour simplifier au début.
 * Pas de TypeConverters nécessaires (priority est stocké en String, createdAt en Long).
 */
@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}