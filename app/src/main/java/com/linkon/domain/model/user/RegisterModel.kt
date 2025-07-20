package com.linkon.domain.model.user

import java.io.Serializable

data class RegisterModel  (
    val code: Int? = 67,
    val msg: String? = null,
    val data: Boolean? = false
) : Serializable