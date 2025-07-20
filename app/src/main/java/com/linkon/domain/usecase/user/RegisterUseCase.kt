package com.linkon.domain.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.RegisterModel
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    operator fun invoke(name: String, phone: String, code: Int)
            : Flow<TaskResult<RegisterModel>>
}