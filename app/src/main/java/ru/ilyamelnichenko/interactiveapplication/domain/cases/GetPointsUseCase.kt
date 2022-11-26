package ru.ilyamelnichenko.interactiveapplication.domain.cases

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import javax.inject.Inject
import ru.ilyamelnichenko.interactiveapplication.domain.UnknownAppError
import ru.ilyamelnichenko.interactiveapplication.repository.InteractiveRepository
import ru.ilyamelnichenko.interactiveapplication.utils.propagateCancellationExceptionHandler

class GetPointsUseCase @Inject constructor(private val repository: InteractiveRepository) :
    UseCase<GetPointsUseCase.Param, GetPointsUseCase.Output> {

    override suspend fun run(param: Param): CaseResult<Output> {

        withContext(Dispatchers.IO + propagateCancellationExceptionHandler()) {
            runCatching {
                repository.points(param.count)
            }
        }
            .onSuccess { response ->
                return Success(
                    Output(
                        points = response
                    )
                )
            }
            .onFailure {
                return Failure(UnknownAppError)
            }

        return Failure(UnknownAppError)
    }

    class Param(val count: Int)

    data class Output(val points: ArrayOfXYResponse)
}