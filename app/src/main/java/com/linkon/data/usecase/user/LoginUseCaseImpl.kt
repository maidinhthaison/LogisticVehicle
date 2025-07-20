package com.linkon.data.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.SessionModel
import com.linkon.domain.repository.user.UserRepository
import com.linkon.domain.usecase.user.LoginUseCase
import kotlinx.coroutines.flow.Flow

class LoginUseCaseImpl (private val userRepository: UserRepository) : LoginUseCase{
    override fun invoke(phone: Long, code: Int, clientId: String): Flow<TaskResult<SessionModel>> {
        return userRepository.login(phone = phone, code = code, clientId = clientId)
    }
}