package com.zerir.weather.repository.remote.retrofit

import com.zerir.weather.repository.data.DefaultResponse
import com.zerir.weather.repository.data.weather.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiReference {

    @GET("current")
    suspend fun getWeatherData(
        @Query("access_key") apiId: String,
        @Query("query") latLon: String
    ): Response<DefaultResponse<Weather>>

}