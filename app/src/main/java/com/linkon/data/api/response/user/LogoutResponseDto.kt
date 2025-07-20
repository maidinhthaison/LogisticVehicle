package com.linkon.data.api.response.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.user.LogoutModel
import java.io.Serializable

@Keep
data class LogoutResponseDto (
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: String?
): Serializable {
    fun toModel(): LogoutModel {
        return LogoutModel(
            code = code,
            msg = msg,
            data = data
        )
    }
}