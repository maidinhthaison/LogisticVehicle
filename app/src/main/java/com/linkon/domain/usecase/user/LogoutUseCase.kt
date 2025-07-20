package com.linkon.domain.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.LogoutModel
import kotlinx.coroutines.flow.Flow

interface LogoutUseCase {
    operator fun invoke()
            : Flow<TaskResult<LogoutModel>>
}