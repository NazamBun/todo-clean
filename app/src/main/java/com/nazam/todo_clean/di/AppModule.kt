package com.nazam.todo_clean.di

import android.content.Context
import androidx.room.Room
import com.nazam.todo_clean.data.local.dao.TaskDao
import com.nazam.todo_clean.data.local.db.AppDatabase
import com.nazam.todo_clean.data.repository.TaskRepositoryImpl
import com.nazam.todo_clean.domain.repository.TaskRepository
import com.nazam.todo_clean.domain.usecase.AddTaskUseCase
import com.nazam.todo_clean.domain.usecase.DeleteTaskUseCase
import com.nazam.todo_clean.domain.usecase.GetTaskByIdUseCase
import com.nazam.todo_clean.domain.usecase.GetTasksUseCase
import com.nazam.todo_clean.domain.usecase.SearchTasksUseCase
import com.nazam.todo_clean.domain.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // --- ROOM DATABASE ---
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todo.db"
        )
            // Pour démarrer simple en dev. (En prod: faire des migrations)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    // --- REPOSITORY ---
    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao
    ): TaskRepository = TaskRepositoryImpl(taskDao)

    // --- USE CASES (un par fichier, pas de wrapper) ---
    @Provides fun provideAddTaskUseCase(repo: TaskRepository) = AddTaskUseCase(repo)
    @Provides fun provideGetTasksUseCase(repo: TaskRepository) = GetTasksUseCase(repo)
    @Provides fun provideUpdateTaskUseCase(repo: TaskRepository) = UpdateTaskUseCase(repo)
    @Provides fun provideDeleteTaskUseCase(repo: TaskRepository) = DeleteTaskUseCase(repo)
    @Provides fun provideSearchTasksUseCase(repo: TaskRepository) = SearchTasksUseCase(repo)
    @Provides fun provideGetTaskByIdUseCase(repo: TaskRepository) = GetTaskByIdUseCase(repo)
}

