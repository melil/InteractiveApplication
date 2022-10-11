package ru.ilyamelnichenko.interactiveapplication.repository

import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse

interface InteractiveRepository {
    suspend fun points(count: Int): ArrayOfXYResponse
}