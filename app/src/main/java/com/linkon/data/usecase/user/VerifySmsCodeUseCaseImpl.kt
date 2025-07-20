package com.linkon.data.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.SmsVerificationModel
import com.linkon.domain.repository.user.UserRepository
import com.linkon.domain.usecase.user.VerifySmsCodeUseCase
import kotlinx.coroutines.flow.Flow

class VerifySmsCodeUseCaseImpl (private val userRepository: UserRepository) :
    VerifySmsCodeUseCase {
    override fun invoke(phone: String, type: String): Flow<TaskResult<SmsVerificationModel>> {
        return  userRepository.verifySmsCode(phone = phone, type = type)
    }
}