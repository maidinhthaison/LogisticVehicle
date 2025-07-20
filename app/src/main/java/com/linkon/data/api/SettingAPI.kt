package com.linkon.data.api

import com.linkon.data.api.response.setting.AboutUsResponseDto
import com.linkon.data.api.response.setting.HelpResponseDto
import com.linkon.data.api.response.setting.PrivacyPolicyResponseDto
import com.linkon.data.api.response.setting.TermOfUserResponseDto
import com.linkon.domain.model.setting.TermOfUserModel
import retrofit2.Response
import retrofit2.http.GET

interface SettingAPI {
    @GET("app/account/terms")
    suspend fun getTermOfUse(
    ): Response<TermOfUserResponseDto>

    @GET("app/account/privacy")
    suspend fun getPrivacyPolicy(
    ): Response<PrivacyPolicyResponseDto>

    @GET("app/account/about")
    suspend fun getAboutUs(
    ): Response<AboutUsResponseDto>

    @GET("app/account/help")
    suspend fun getHelpCenter(
    ): Response<HelpResponseDto>

}