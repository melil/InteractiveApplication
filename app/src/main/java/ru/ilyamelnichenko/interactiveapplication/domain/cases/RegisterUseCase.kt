package ru.ilyamelnichenko.interactiveapplication.domain.cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ilyamelnichenko.interactiveapplication.api.requests.RegisterRequest
import ru.ilyamelnichenko.interactiveapplication.api.responses.RegisterResponse
import ru.ilyamelnichenko.interactiveapplication.domain.UnknownAppError
import ru.ilyamelnichenko.interactiveapplication.repository.InteractiveRepository
import ru.ilyamelnichenko.interactiveapplication.utils.propagateCancellationExceptionHandler
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: InteractiveRepository) :
    UseCase<RegisterUseCase.Param, RegisterUseCase.Output> {

    override suspend fun run(param: Param): CaseResult<Output> {

        withContext(Dispatchers.IO + propagateCancellationExceptionHandler()) {
            runCatching {
                repository.register(
                    RegisterRequest(
                        name = param.name,
                        email = param.email,
                        phone = param.phone
                    )
                )
            }
        }.onSuccess {
            return Success(
                Output(it)
            )
        }
            .onFailure {
                return Failure(UnknownAppError)
            }


        return Failure(UnknownAppError)
    }


    class Param(val name: String, val email: String, val phone: String)

    data class Output(val output: RegisterResponse)


}