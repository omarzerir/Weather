package com.zerir.weather.repository.data.weather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Weather(
    @SerializedName("current")
    val current: Current? = null,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("request")
    val request: Request? = null
) : Serializable {

    fun customString(): String {
        return "${location?.region}, ${location?.country}\nTemp:${current?.temperature}, Hum:${current?.humidity}, ${current?.desc()}"
    }

}