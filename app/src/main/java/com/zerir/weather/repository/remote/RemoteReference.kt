package com.zerir.weather.repository.remote

import com.zerir.weather.repository.data.weather.Weather
import com.zerir.weather.repository.remote.retrofit.ApiResult

interface RemoteReference {

    suspend fun getWeatherData(city: String): ApiResult<Weather?>

}