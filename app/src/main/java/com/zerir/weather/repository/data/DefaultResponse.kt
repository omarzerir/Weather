package com.zerir.weather.repository.data

import com.zerir.weather.repository.data.error.ErrorResponse
import java.io.Serializable

data class DefaultResponse<T>(
    val successBody: T? = null,
    val error: ErrorResponse? = null
) : Serializable
