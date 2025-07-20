package com.linkon.domain.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.SmsVerificationModel
import kotlinx.coroutines.flow.Flow

interface VerifySmsCodeUseCase {
    operator fun invoke(phone: String, type: String)
            : Flow<TaskResult<SmsVerificationModel>>
}