package com.zerir.weather.repository.remote.retrofit

import com.google.gson.GsonBuilder
import com.zerir.weather.BuildConfig
import com.zerir.weather.repository.data.DefaultResponse
import com.zerir.weather.repository.data.customs.ResponseDeserializer
import com.zerir.weather.repository.data.weather.Weather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private const val BASE_URL = "http://api.weatherstack.com/"
        const val apiId = BuildConfig.WeatherKey
    }

    fun clientApis(): ApiReference {
        val gson = GsonBuilder()
            .registerTypeAdapter(DefaultResponse::class.java, ResponseDeserializer(Weather::class.java))
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


        return retrofit.create(ApiReference::class.java)
    }

}