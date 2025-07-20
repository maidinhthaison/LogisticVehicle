package com.linkon.domain.usecase.setting

import com.linkon.domain.TaskResult
import com.linkon.domain.model.setting.TermOfUserModel
import kotlinx.coroutines.flow.Flow

interface GetTermOfUseUseCase {
    operator fun invoke()
            : Flow<TaskResult<TermOfUserModel>>
}