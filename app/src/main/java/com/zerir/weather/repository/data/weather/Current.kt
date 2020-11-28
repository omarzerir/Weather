package com.zerir.weather.repository.data.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Current(
    @SerializedName("cloudcover")
    val cloudcover: Int = 0,
    @SerializedName("feelslike")
    val feelslike: Int = 0,
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("is_day")
    val isDay: String = "",
    @SerializedName("observation_time")
    val observationTime: String = "",
    @SerializedName("precip")
    val precip: Int = 0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("temperature")
    val temperature: Int = 0,
    @SerializedName("uv_index")
    val uvIndex: Int = 0,
    @SerializedName("visibility")
    val visibility: Int = 0,
    @SerializedName("weather_code")
    val weatherCode: Int = 0,
    @SerializedName("weather_descriptions")
    val weatherDescriptions: ArrayList<String> = ArrayList(),
    @SerializedName("weather_icons")
    val weatherIcons: ArrayList<String> = ArrayList(),
    @SerializedName("wind_degree")
    val windDegree: Int = 0,
    @SerializedName("wind_dir")
    val windDir: String = "",
    @SerializedName("wind_speed")
    val windSpeed: Int = 0
) : Serializable {

    fun desc(): String {
        val text = StringBuilder()
        text.append("")
        for(d in weatherDescriptions){
            text.append(" $d")
        }
        return text.toString()
    }

}