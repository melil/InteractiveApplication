package ru.ilyamelnichenko.interactiveapplication.domain.cases

interface UseCase<Input, Output> {
    suspend fun run(param: Input): CaseResult<Output>
}
