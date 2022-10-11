package ru.ilyamelnichenko.interactiveapplication.di.component

import dagger.Component
import ru.ilyamelnichenko.interactiveapplication.App
import ru.ilyamelnichenko.interactiveapplication.api.ApiModule
import ru.ilyamelnichenko.interactiveapplication.di.module.ApplicationModule
import ru.ilyamelnichenko.interactiveapplication.di.module.NetworkConfigModule
import ru.ilyamelnichenko.interactiveapplication.repository.InteractiveRepositoryModule
import ru.ilyamelnichenko.interactiveapplication.ui.main.MainFragment
import ru.ilyamelnichenko.interactiveapplication.ui.main.di.MainComponent
import ru.ilyamelnichenko.interactiveapplication.ui.main.di.MainModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ApiModule::class,
        InteractiveRepositoryModule::class,
        NetworkConfigModule::class
    ]
)

interface AppComponent {
    fun inject(app: App)
    fun inject(fragment: MainFragment)
}