package ru.ilyamelnichenko.interactiveapplication.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler

fun propagateCancellationExceptionHandler() =
    CoroutineExceptionHandler { _, exception ->
        if (exception is CancellationException) {
            throw exception
        }
    }
