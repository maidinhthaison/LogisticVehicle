package com.linkon.data.api.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginRequestDto(
    @SerializedName("phone") var phone: Long?,
    @SerializedName("code") var code: Int?,
    @SerializedName("clientId") var clientId: String?
) : Serializable