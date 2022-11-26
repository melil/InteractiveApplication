package ru.ilyamelnichenko.interactiveapplication.repository

import ru.ilyamelnichenko.interactiveapplication.api.requests.RegisterRequest
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.api.responses.RegisterResponse

interface InteractiveRepository {
    suspend fun points(count: Int): ArrayOfXYResponse
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse
}