package com.linkon.domain.repository.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.SessionModel
import com.linkon.domain.model.user.LogoutModel
import com.linkon.domain.model.user.RegisterModel
import com.linkon.domain.model.user.SmsVerificationModel
import com.linkon.domain.model.user.UserInfoModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(phone: Long, code : Int, clientId: String): Flow<TaskResult<SessionModel>>
    fun register(name : String, phone : String, code : Int): Flow<TaskResult<RegisterModel>>
    fun getUserInfo(): Flow<TaskResult<UserInfoModel>>
    fun verifySmsCode(phone : String, type: String): Flow<TaskResult<SmsVerificationModel>>
    fun logout(): Flow<TaskResult<LogoutModel>>
}