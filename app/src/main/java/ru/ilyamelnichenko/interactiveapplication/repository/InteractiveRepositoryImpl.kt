package ru.ilyamelnichenko.interactiveapplication.repository

import android.app.Application
import com.google.gson.Gson
import ru.ilyamelnichenko.interactiveapplication.BuildConfig
import ru.ilyamelnichenko.interactiveapplication.api.InteractiveApi
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.auxiliary.readAssetsFile
import ru.ilyamelnichenko.interactiveapplication.di.ApplicationContext
import javax.inject.Inject

class InteractiveRepositoryImpl @Inject constructor(
    private val api: InteractiveApi,
    @ApplicationContext private val context: Application,
) : InteractiveRepository {

    override suspend fun points(count: Int): ArrayOfXYResponse {
        return if(BuildConfig.FLAVOR == "mock") {
             Gson().fromJson(
                context.assets?.readAssetsFile("get_points.json"),
                ArrayOfXYResponse::class.java
            )
        } else api.points(count)
    }

}