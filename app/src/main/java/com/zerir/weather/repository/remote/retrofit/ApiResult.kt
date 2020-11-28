package com.zerir.weather.repository.remote.retrofit

import com.zerir.weather.repository.data.DefaultResponse
import retrofit2.Response

class ApiResult<out T> private constructor(
    val apiStatusType: ApiStatusType,
    val data: T?
) {

    companion object {

        private fun <T> success(data: T?): ApiResult<T> {
            val apiStatusType = ApiStatusType.SUCCESS
            apiStatusType.data = data
            return ApiResult(apiStatusType, data)
        }

        private fun <T> failure(message: String?): ApiResult<T> {
            val apiStatusType = ApiStatusType.FAILURE
            apiStatusType.data = message
            return ApiResult(apiStatusType, null)
        }

        fun <T> result(response: Response<DefaultResponse<T>>?): ApiResult<T?> {
            return if (response == null) failure("Something Went Wrong")
            else {
                return if (response.isSuccessful) {
                    val data = response.body()
                    return if(data == null) failure("Something Went Wrong")
                    else if(data.error == null && data.successBody == null) failure("Something Went Wrong")
                    else if(data.error != null) {
                        val message = data.error.info
                        failure(message)
                    }else {
                        success(data.successBody)
                    }
                } else {
                    failure("Something Went Wrong")
                }
            }
        }
    }

}

enum class ApiStatusType(var data: Any?) {
    SUCCESS(null),
    FAILURE(null),
    LOADING(null)
}