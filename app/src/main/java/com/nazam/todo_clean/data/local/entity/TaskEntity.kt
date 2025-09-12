package com.nazam.todo_clean.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Table Room "tasks".
 * Simple : on stocke Priority en String et createdAt en Long (pas de converter pour Long).
 */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val priority: String = "MEDIUM", // LOW / MEDIUM / HIGH (on convertira vers enum dans le mapper)
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
