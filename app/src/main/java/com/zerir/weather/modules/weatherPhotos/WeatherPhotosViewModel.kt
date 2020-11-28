package com.zerir.weather.modules.weatherPhotos

import android.app.Activity
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zerir.weather.modules.weatherPhotos.adapter.WeatherPhotosAdapter
import com.zerir.weather.repository.RepoManager
import com.zerir.weather.utilities.LocationHandler

class WeatherPhotosViewModel(app: Application) : AndroidViewModel(app),
    WeatherPhotosAdapter.OnPhotoClickListener, LocationHandler.OnLocationResult {

    private val repoManager = RepoManager.getInstance(app.applicationContext)

    val photos: LiveData<ArrayList<String>> get() = repoManager.getAllWeatherPhotos()

    val weatherPhotosAdapter = WeatherPhotosAdapter(this)

    private val mDisplayPhoto = MutableLiveData<String>()
    val displayPhoto: LiveData<String> get() = mDisplayPhoto

    private val mLatLon = MutableLiveData<String>()
    val latLon: LiveData<String> get() = mLatLon

    private val mProvider = MutableLiveData<Boolean>()
    val provider: LiveData<Boolean> get() = mProvider

    private lateinit var locationHandler: LocationHandler

    fun requestLocation(context: Activity){
        if(!::locationHandler.isInitialized) locationHandler = LocationHandler(context, this)
        locationHandler.getLastLocation()
    }

    override fun onPhotoClicked(path: String) {
        mDisplayPhoto.value = path
    }

    override fun onLocationGet(location: Location) {
        val latLon = "${location.latitude},${location.longitude}"
        mLatLon.value = latLon
    }

    override fun onLocationIsNotEnabled() {
        mProvider.value = true
    }

    fun clearDisplayPhoto(){
        mDisplayPhoto.value = null
    }

    fun clearLatLon(){
        mLatLon.value = null
    }

    fun clearProvider(){
        mProvider.value = null
    }
}