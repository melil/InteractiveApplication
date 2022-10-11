package ru.ilyamelnichenko.interactiveapplication.api

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ilyamelnichenko.interactiveapplication.BuildConfig
import ru.ilyamelnichenko.interactiveapplication.di.BaseUrl
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    fun providesApi(okHttpClient: OkHttpClient, @BaseUrl baseUrl: String): InteractiveApi {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return builder.create(InteractiveApi::class.java)
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

}