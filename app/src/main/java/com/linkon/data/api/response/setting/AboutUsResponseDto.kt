package com.linkon.data.api.response.setting

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.setting.AboutUsModel
import java.io.Serializable

@Keep
class AboutUsResponseDto(
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: String?,
) : Serializable {
    fun toAboutUsModel(): AboutUsModel {
        return AboutUsModel(
            code = code,
            msg = msg,
            dataResponse = data
        )
    }
}