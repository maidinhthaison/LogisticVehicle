package com.linkon.domain.model.user

import java.io.Serializable

data class LogoutModel  (
    val code: Int? = null,
    val msg: String? = null,
    val data: String? = null
) : Serializable