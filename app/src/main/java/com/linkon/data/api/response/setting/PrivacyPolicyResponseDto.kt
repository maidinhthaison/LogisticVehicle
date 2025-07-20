package com.linkon.data.api.response.setting

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.setting.PrivacyPolicyModel
import java.io.Serializable

@Keep
class PrivacyPolicyResponseDto(
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: String?,
) : Serializable {
    fun toPrivacyPolicyModel(): PrivacyPolicyModel {
        return PrivacyPolicyModel(
            code = code,
            msg = msg,
            dataResponse = data
        )
    }
}