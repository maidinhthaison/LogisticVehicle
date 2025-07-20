package com.linkon.data.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.RegisterModel
import com.linkon.domain.repository.user.UserRepository
import com.linkon.domain.usecase.user.RegisterUseCase
import kotlinx.coroutines.flow.Flow

class RegisterUseCaseCaseImpl (private val userRepository: UserRepository) : RegisterUseCase {
    override fun invoke(name: String, phone: String, code: Int): Flow<TaskResult<RegisterModel>> {
        return userRepository.register(name = name, phone = phone, code = code)
    }
}