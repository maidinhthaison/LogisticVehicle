package com.linkon.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class APIResponseErrorDto (
    @SerializedName("code")
    val code: Int?,
    @SerializedName("msg")
    val msg: String?
) : Serializable {
    companion object {
        fun fromJson(json: String): APIResponseErrorDto? {
            try {
                return Gson().fromJson(json, APIResponseErrorDto::class.java)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }
    }

}