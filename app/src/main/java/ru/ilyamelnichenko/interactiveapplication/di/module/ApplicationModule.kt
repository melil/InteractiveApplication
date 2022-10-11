package ru.ilyamelnichenko.interactiveapplication.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.ilyamelnichenko.interactiveapplication.di.ApplicationContext
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Application = application
}