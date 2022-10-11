package ru.ilyamelnichenko.interactiveapplication.di.component

import dagger.Component
import ru.ilyamelnichenko.interactiveapplication.App
import ru.ilyamelnichenko.interactiveapplication.api.ApiModule
import ru.ilyamelnichenko.interactiveapplication.di.module.ApplicationModule
import ru.ilyamelnichenko.interactiveapplication.di.module.NetworkConfigModule
import ru.ilyamelnichenko.interactiveapplication.repository.InteractiveRepositoryModule
import ru.ilyamelnichenko.interactiveapplication.ui.chart.canvas.CanvasFragment
import ru.ilyamelnichenko.interactiveapplication.ui.chart.mpandroidchart.MPAndroidChartFragment
import ru.ilyamelnichenko.interactiveapplication.ui.main.MainFragment
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
    fun inject(fragment: CanvasFragment)
    fun inject(fragment: MPAndroidChartFragment)
}