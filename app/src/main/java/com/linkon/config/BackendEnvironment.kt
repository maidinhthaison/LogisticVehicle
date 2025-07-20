package com.linkon.config

import com.linkon.BuildConfig


enum class BackendEnvironment(
    val baseUrl: String = "http://47.81.15.63:8090/"
) {
    Dev(
        baseUrl = BuildConfig.BASE_API_URL
    ),

    Prod(
        baseUrl = BuildConfig.BASE_API_URL
    )
}