package ru.ilyamelnichenko.interactiveapplication.repository

import dagger.Binds
import dagger.Module

@Module
abstract class InteractiveRepositoryModule {

    @Binds
    abstract fun bindsInteractiveRepository(impl: InteractiveRepositoryImpl): InteractiveRepository
}