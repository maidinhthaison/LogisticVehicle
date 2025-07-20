package com.linkon.data.api

import com.linkon.data.api.request.LoginRequestDto
import com.linkon.data.api.request.RegisterRequestDto
import com.linkon.data.api.response.orders.OrderResponseDto
import com.linkon.data.api.response.user.LoginResponseDto
import com.linkon.data.api.response.user.LogoutResponseDto
import com.linkon.data.api.response.user.RegisterResponseDto
import com.linkon.data.api.response.user.SmsVerificationResponseDto
import com.linkon.data.api.response.user.UserInfoResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPI {

    @POST("app/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<LoginResponseDto>

    @POST("app/driver/register")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): Response<RegisterResponseDto>

    @GET("app/user")
    suspend fun getUserInfo(
    ): Response<UserInfoResponseDto>

    @GET("app/sms")
    suspend fun verifySmsCode(
        @Query("phone") phone: String?,
        @Query("type") type: String?,
    ): Response<SmsVerificationResponseDto>

    @GET("app/logout")
    suspend fun logout(
    ): Response<LogoutResponseDto>

}