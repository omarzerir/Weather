package com.zerir.weather.repository

import android.content.Context
import com.zerir.weather.repository.data.weather.Weather
import com.zerir.weather.repository.local.LocalRepo
import com.zerir.weather.repository.remote.RemoteRepo
import com.zerir.weather.repository.remote.retrofit.ApiResult

class RepoManager(context: Context) : RepoReference {

    private val localRepo = LocalRepo(context)
    private val remoteRepo = RemoteRepo()

    companion object {
        private var INSTANCE: RepoManager? = null

        fun getInstance(context: Context): RepoManager {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = RepoManager(context)
                }
                return INSTANCE as RepoManager
            }
        }
    }

    override suspend fun getWeatherData(city: String): ApiResult<Weather?> {
        return remoteRepo.getWeatherData(city)
    }

}