package ru.ilyamelnichenko.interactiveapplication.di.module

import dagger.Module
import dagger.Provides
import ru.ilyamelnichenko.interactiveapplication.di.BaseUrl

@Module
class NetworkConfigModule(
    private val baseUrl: String
) {
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = baseUrl
}