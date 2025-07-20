package com.linkon.domain.model.user

import java.io.Serializable

data class SessionDataModel (
    val scope: String? = null,
    val openid: String? = null,
    val accessToken: String?,
    val refreshToken: String? = null,
    val expireIn: Int? = null,
    val refreshExpireIn: Int? = null,
    val clientId: String? = "app_driver"
): Serializable