package com.nazam.todo_clean.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Configuration Hilt uniquement
    // Ici vous pourrez ajouter vos providers @Provides et @Binds
}

