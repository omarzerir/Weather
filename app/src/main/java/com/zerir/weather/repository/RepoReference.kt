package com.zerir.weather.repository

import com.zerir.weather.repository.data.weather.Weather
import com.zerir.weather.repository.remote.retrofit.ApiResult

interface RepoReference {

    suspend fun getWeatherData(city: String): ApiResult<Weather?>

}