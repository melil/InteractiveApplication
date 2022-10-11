package ru.ilyamelnichenko.interactiveapplication.domain.cases

import ru.ilyamelnichenko.interactiveapplication.domain.AppError

sealed class CaseResult<T> {
    suspend fun onSuccess(onSuccess: suspend (result: T) -> Unit): CaseResult<T> {

        if (this is Success) {
            onSuccess.invoke(result)
        }

        return this
    }

    suspend fun onFailure(onFailure: suspend (error: AppError) -> Unit): CaseResult<T> {

        if (this is Failure) {
            onFailure.invoke(error)
        }

        return this
    }

    val isSuccessFul: Boolean
        get() = this is Success

    val isFailure: Boolean
        get() = this is Failure

    val appError: AppError?
        get() = (this as? Failure)?.error
}

data class Success<T>(val result: T) : CaseResult<T>()
data class Failure<T>(val error: AppError) : CaseResult<T>()
