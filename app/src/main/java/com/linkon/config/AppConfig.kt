package com.linkon.config

object AppConfig {
    /**
     * Get environment from current build type
     */
    private var backendEnvironment: BackendEnvironment = BackendEnvironment.Dev
    private fun getFromBuildType(flavor: String): BackendEnvironment {
        return when (flavor) {
            "dev" -> BackendEnvironment.Dev
            else -> BackendEnvironment.Prod
        }
    }

    fun setBackendEnvironment(flavor: String) {
        backendEnvironment = getFromBuildType(flavor)
    }
}