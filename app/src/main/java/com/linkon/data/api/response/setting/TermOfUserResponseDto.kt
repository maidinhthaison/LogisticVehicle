package com.linkon.data.api.response.setting

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.setting.TermOfUserModel
import java.io.Serializable

@Keep
class TermOfUserResponseDto(
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: String?,
) : Serializable {
    fun toTermOfUserModel(): TermOfUserModel {
        return TermOfUserModel(
            code = code,
            msg = msg,
            dataResponse = data
        )
    }
}