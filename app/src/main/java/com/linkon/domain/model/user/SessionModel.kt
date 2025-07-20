package com.linkon.domain.model.user

import java.io.Serializable

data class SessionModel (
    val code: Int? = null,
    val msg: String? = null,
    val data: SessionDataModel? = null
) : Serializable