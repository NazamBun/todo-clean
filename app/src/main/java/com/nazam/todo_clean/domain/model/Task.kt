package com.nazam.todo_clean.domain


data class Task(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val priority: Priority = Priority.MEDIUM,
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class Priority { LOW, MEDIUM, HIGH }