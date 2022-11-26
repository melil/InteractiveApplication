package ru.ilyamelnichenko.interactiveapplication.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.ilyamelnichenko.interactiveapplication.api.requests.RegisterRequest
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.api.responses.RegisterResponse

interface InteractiveApi {

    companion object {
        const val POINTS = "api/test/points/{count}"
        const val REGISTER = "api/test/points/{count}"
    }

    @GET(POINTS)
    suspend fun points(@Path("count") count: Int): ArrayOfXYResponse

    @POST(REGISTER)
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse
}