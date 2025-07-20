package com.linkon.domain.model.user

import java.io.Serializable

data class SmsVerificationModel (
    val code: Int? = null,
    val msg: String? = null,
    val data: Boolean? = null
) : Serializable