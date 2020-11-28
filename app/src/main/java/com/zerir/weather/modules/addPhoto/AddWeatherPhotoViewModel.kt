package com.zerir.weather.modules.addPhoto

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zerir.weather.repository.RepoManager
import com.zerir.weather.repository.data.weather.Weather
import com.zerir.weather.repository.remote.retrofit.ApiStatusType
import com.zerir.weather.utilities.drawMultilineTextToBitmap
import kotlinx.coroutines.*

class AddWeatherPhotoViewModel(private val app: Application) : AndroidViewModel(app) {

    private val repoManager = RepoManager.getInstance(app.applicationContext)

    private val scoop = CoroutineScope(Job() + Dispatchers.IO)

    private val mWeatherStatus = MutableLiveData<ApiStatusType>()
    val weatherStatus: LiveData<ApiStatusType> get() = mWeatherStatus

    private val mFinalPhoto = MutableLiveData<String>()
    val finalPhoto: LiveData<String> get() = mFinalPhoto

    fun getWeatherData(latLon: String){
        scoop.launch {
            mWeatherStatus.postValue(ApiStatusType.LOADING)
            val status = repoManager.getWeatherData(latLon).apiStatusType
            mWeatherStatus.postValue(status)
        }
    }

    fun onWeatherDataGot(bitmap: Bitmap, weather: Weather){
        val text = weather.customString()
        createPhoto(bitmap, text)
    }

    private fun createPhoto(bitmap: Bitmap, text: String){
        CoroutineScope(Dispatchers.Default).launch {
            val photoPath = drawMultilineTextToBitmap(
                app.applicationContext,
                bitmap,
                text
            )
            mFinalPhoto.postValue(photoPath)
            repoManager.addWeatherPhoto(photoPath)
        }
    }

    fun clearWeatherStatus(){
        mWeatherStatus.value = null
    }

    override fun onCleared() {
        super.onCleared()
        scoop.cancel()
    }

}