package com.zerir.weather.repository

import androidx.lifecycle.LiveData
import com.zerir.weather.repository.data.weather.Weather
import com.zerir.weather.repository.remote.retrofit.ApiResult

interface RepoReference {

    suspend fun getWeatherData(city: String): ApiResult<Weather?>

    suspend fun addWeatherPhoto(path: String?)

    fun getAllWeatherPhotos(): LiveData<ArrayList<String>>
}