package ru.ilyamelnichenko.interactiveapplication.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse

interface InteractiveApi {

    companion object {
        const val POINTS = "api/test/points/{count}"
    }

    @GET(POINTS)
    suspend fun points(@Path("count") count: Int): ArrayOfXYResponse
}