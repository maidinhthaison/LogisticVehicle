package com.linkon.data.api.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterRequestDto (
    @SerializedName("phone") var phone: String?,
    @SerializedName("code") var code: Int?,
    @SerializedName("name") var name: String?
) : Serializable