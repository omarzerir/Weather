package com.zerir.weather.modules.weatherPhotos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zerir.weather.modules.weatherPhotos.adapter.WeatherPhotosAdapter
import com.zerir.weather.repository.RepoManager

class WeatherPhotosViewModel(app: Application) : AndroidViewModel(app), WeatherPhotosAdapter.OnPhotoClickListener {

    private val repoManager = RepoManager.getInstance(app.applicationContext)

    val photos: LiveData<ArrayList<String>> get() = repoManager.getAllWeatherPhotos()

    val weatherPhotosAdapter = WeatherPhotosAdapter(this)

    private val mDisplayPhoto = MutableLiveData<String>()
    val displayPhoto: LiveData<String> get() = mDisplayPhoto

    override fun onPhotoClicked(path: String) {
        mDisplayPhoto.value = path
    }

    fun clearDisplayPhoto(){
        mDisplayPhoto.value = null
    }

}