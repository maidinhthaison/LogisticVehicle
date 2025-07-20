package com.linkon.domain.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.SessionModel
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    operator fun invoke(phone: Long, code: Int, clientId: String)
            : Flow<TaskResult<SessionModel>>
}