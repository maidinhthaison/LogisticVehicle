package com.linkon.data.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.UserInfoModel
import com.linkon.domain.repository.user.UserRepository
import com.linkon.domain.usecase.user.GetUserInfoUseCase
import kotlinx.coroutines.flow.Flow

class GetUserInfoUseCaseImpl (private val userRepository: UserRepository) : GetUserInfoUseCase {

    override fun invoke(): Flow<TaskResult<UserInfoModel>> {
        return userRepository.getUserInfo()
    }
}