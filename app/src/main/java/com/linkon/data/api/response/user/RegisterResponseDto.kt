package com.linkon.data.api.response.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.user.RegisterModel
import java.io.Serializable

@Keep
data class RegisterResponseDto (
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: Boolean?
): Serializable {
    fun toRegisterModel(): RegisterModel {
        return RegisterModel(
            code = code,
            msg = msg,
            data = data
        )
    }
}