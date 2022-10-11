package ru.ilyamelnichenko.interactiveapplication.repository

import ru.ilyamelnichenko.interactiveapplication.api.InteractiveApi
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import javax.inject.Inject

class InteractiveRepositoryImpl @Inject constructor(
    private val api: InteractiveApi
) : InteractiveRepository {

    override suspend fun points(count: Int): ArrayOfXYResponse = api.points(count)

}