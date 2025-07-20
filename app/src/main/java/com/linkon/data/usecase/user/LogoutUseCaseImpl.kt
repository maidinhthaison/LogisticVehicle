package com.linkon.data.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.LogoutModel
import com.linkon.domain.repository.user.UserRepository
import com.linkon.domain.usecase.user.LogoutUseCase
import kotlinx.coroutines.flow.Flow

class LogoutUseCaseImpl (private val userRepository: UserRepository) : LogoutUseCase {

    override fun invoke(): Flow<TaskResult<LogoutModel>> {
        return userRepository.logout()
    }
}