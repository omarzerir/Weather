package com.zerir.weather.repository.local

import androidx.lifecycle.LiveData

interface LocalReference {

    suspend fun addWeatherPhoto(path: String?)

    fun getAllWeatherPhotos(): LiveData<ArrayList<String>>

}