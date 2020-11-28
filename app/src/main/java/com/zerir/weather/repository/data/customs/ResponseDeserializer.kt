package com.zerir.weather.repository.data.customs

import com.google.gson.*
import com.zerir.weather.repository.data.error.ErrorResponse
import com.zerir.weather.repository.data.DefaultResponse
import java.lang.reflect.Type

class ResponseDeserializer<T>(private val cls: Class<T>) : JsonDeserializer<DefaultResponse<T>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DefaultResponse<T> {
        val jsonObject = json?.asJsonObject
        jsonObject?.let {
            return if (it.has("error")) {
                val error = Gson().fromJson(it.get("error"), ErrorResponse::class.java)
                DefaultResponse(null, error)
            } else {
                val success = Gson().fromJson(jsonObject, cls)
                DefaultResponse(success, null)
            }
        }
        return DefaultResponse(null, null)
    }
}