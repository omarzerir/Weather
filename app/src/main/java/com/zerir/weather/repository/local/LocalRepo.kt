package com.zerir.weather.repository.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier

class LocalRepo(context: Context) : LocalReference {

    companion object {
        private const val STORAGE_KEY = "weather-storage"
        private const val PHOTOS_KEY = "weather-photos"
    }

    private val sharedPreferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
    private val prefsEditor = sharedPreferences.edit()

    //modifier is add if model contain some variables can't converting
    private val gson = GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .create()

    private val mPhotos = MutableLiveData<ArrayList<String>>()

    override suspend fun addWeatherPhoto(path: String?) {
        if(path == null) return
        val list = getPhotos()
        list.add(path)
        savePhotos(list)
    }

    override fun getAllWeatherPhotos(): LiveData<ArrayList<String>> {
        val list = getPhotos()
        mPhotos.postValue(list)
        return mPhotos
    }

    private fun savePhotos(list: ArrayList<String>){
        val json = gson.toJson(list)
        prefsEditor?.putString(PHOTOS_KEY, json)
        prefsEditor?.apply()
        mPhotos.postValue(list)
    }

    private fun getPhotos(): ArrayList<String> {
        val json = sharedPreferences.getString(PHOTOS_KEY, null)
        @Suppress("UNCHECKED_CAST")
        return gson.fromJson(json, ArrayList::class.java) as ArrayList<String>? ?: ArrayList()
    }

}