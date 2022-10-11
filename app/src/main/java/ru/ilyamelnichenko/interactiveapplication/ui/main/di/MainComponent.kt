package ru.ilyamelnichenko.interactiveapplication.ui.main.di

import dagger.Subcomponent
import ru.ilyamelnichenko.interactiveapplication.ui.main.MainFragment

@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    fun inject(fragment: MainFragment)
}