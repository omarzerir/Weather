package com.zerir.weather.repository.remote

import android.util.Log
import com.zerir.weather.repository.data.weather.Weather
import com.zerir.weather.repository.remote.retrofit.ApiClient
import com.zerir.weather.repository.remote.retrofit.ApiResult

class RemoteRepo : RemoteReference {

    private val clientApis = ApiClient().clientApis()
    private val apiId get() = ApiClient.apiId

    override suspend fun getWeatherData(latLon: String): ApiResult<Weather?> {
        return try {
            val response = clientApis.getWeatherData(apiId = apiId, latLon = latLon)
            ApiResult.result(response)
        } catch (e: Throwable) {
            Log.e("weather ex", "${e.message}")
            ApiResult.result(null)
        }
    }



}