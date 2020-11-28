package com.zerir.weather.repository.data.error

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorResponse(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("type")
    val type: String = "",
    @SerializedName("info")
    val info: String = "",
) : Serializable