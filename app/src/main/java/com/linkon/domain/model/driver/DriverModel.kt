package com.linkon.domain.model.driver

import java.io.Serializable

data class DriverModel (
    val id: String? = null,
    val name: String? = null,
    val licensePlate: String? = null
) : Serializable