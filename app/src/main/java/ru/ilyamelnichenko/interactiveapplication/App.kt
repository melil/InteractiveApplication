package ru.ilyamelnichenko.interactiveapplication

import android.app.Application
import ru.ilyamelnichenko.interactiveapplication.api.ApiModule
import ru.ilyamelnichenko.interactiveapplication.di.component.AppComponent
import ru.ilyamelnichenko.interactiveapplication.di.component.DaggerAppComponent
import ru.ilyamelnichenko.interactiveapplication.di.module.ApplicationModule
import ru.ilyamelnichenko.interactiveapplication.di.module.NetworkConfigModule

open class App: Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        createComponent()
    }

    protected open fun createComponent() {
        component = DaggerAppComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .apiModule(ApiModule())
                .networkConfigModule(NetworkConfigModule(BuildConfig.API_BASE_URL))
                .build()

    }
}