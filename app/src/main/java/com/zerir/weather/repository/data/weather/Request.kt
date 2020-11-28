package com.zerir.weather.repository.data.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Request(
    @SerializedName("language")
    val language: String = "",
    @SerializedName("query")
    val query: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("unit")
    val unit: String = ""
) : Serializable