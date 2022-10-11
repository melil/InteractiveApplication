package ru.ilyamelnichenko.interactiveapplication.repository

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.ilyamelnichenko.interactiveapplication.App
import ru.ilyamelnichenko.interactiveapplication.api.InteractiveApi
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.auxiliary.readAssetsFile
import ru.ilyamelnichenko.interactiveapplication.di.ApplicationContext
import ru.ilyamelnichenko.interactiveapplication.di.module.ApplicationModule
import javax.inject.Inject

class InteractiveRepositoryImpl @Inject constructor(
    private val api: InteractiveApi,
    @ApplicationContext private val context: Application,
) : InteractiveRepository {

    override suspend fun points(count: Int): ArrayOfXYResponse {
        return Gson().fromJson(
            context.assets?.readAssetsFile("get_points.json"),
            ArrayOfXYResponse::class.java
        )
        return api.points(count)
    }

}