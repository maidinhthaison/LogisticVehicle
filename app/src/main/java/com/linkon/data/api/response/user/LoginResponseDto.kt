package com.linkon.data.api.response.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.user.SessionDataModel
import com.linkon.domain.model.user.SessionModel
import java.io.Serializable


@Keep
data class LoginResponseDto (
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: LoginDataResponseDto?
): Serializable {
    fun toSessionModel(): SessionModel {
        return SessionModel(
            code = code,
            msg = msg,
            data = data?.toSessionDataModel()
        )
    }
}

@Keep
data class LoginDataResponseDto (
    @SerializedName("scope") val scope: String?,
    @SerializedName("openid") val openid: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("expire_in") val expireIn: Int?,
    @SerializedName("refresh_expire_in") val refreshExpireIn: Int?,
    @SerializedName("client_id") val clientId: String?,
): Serializable {
    fun toSessionDataModel(): SessionDataModel {
        return SessionDataModel(
            scope = scope,
            openid = openid,
            accessToken = accessToken,
            refreshToken = refreshToken,
            expireIn = expireIn,
            refreshExpireIn = refreshExpireIn,
            clientId  = clientId
        )
    }
}