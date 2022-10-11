package ru.ilyamelnichenko.interactiveapplication.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class AppError: Parcelable

@Parcelize
object ApiError : AppError()

@Parcelize
object AndroidError : AppError()

@Parcelize
object UnknownAppError : AppError()