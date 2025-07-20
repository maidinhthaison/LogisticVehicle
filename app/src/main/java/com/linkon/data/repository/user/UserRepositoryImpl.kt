package com.linkon.data.repository.user

import com.linkon.data.SafeCallAPI
import com.linkon.data.api.UserAPI
import com.linkon.data.api.request.LoginRequestDto
import com.linkon.data.api.request.RegisterRequestDto
import com.linkon.domain.TaskResult
import com.linkon.domain.map
import com.linkon.domain.model.user.SessionModel
import com.linkon.domain.model.user.LogoutModel
import com.linkon.domain.model.user.RegisterModel
import com.linkon.domain.model.user.SmsVerificationModel
import com.linkon.domain.model.user.UserInfoModel
import com.linkon.domain.repository.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl (private val userAPI: UserAPI,
                          private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO) : UserRepository {
    override fun login(phone: Long, code: Int, clientId: String): Flow<TaskResult<SessionModel>> = flow {
        emit(TaskResult.Loading)
        val loginRequestDto = LoginRequestDto(phone, code, clientId)
        val result = SafeCallAPI.callApi {
            userAPI.login(loginRequestDto)
        }.map { it.toSessionModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun register(name: String, phone: String, code: Int): Flow<TaskResult<RegisterModel>> = flow {
        emit(TaskResult.Loading)
        val registerRequestDto = RegisterRequestDto(phone, code, name)
        val result = SafeCallAPI.callApi {
            userAPI.register(registerRequestDto)
        }.map { it.toRegisterModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getUserInfo(): Flow<TaskResult<UserInfoModel>>  = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            userAPI.getUserInfo()
        }.map { it.toModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun verifySmsCode(
        phone: String,
        type: String
    ): Flow<TaskResult<SmsVerificationModel>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            userAPI.verifySmsCode(phone, type)
        }.map { it.toModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun logout(): Flow<TaskResult<LogoutModel>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            userAPI.logout()
        }.map { it.toModel() }
        emit(result)
    }.flowOn(defaultDispatcher)
}