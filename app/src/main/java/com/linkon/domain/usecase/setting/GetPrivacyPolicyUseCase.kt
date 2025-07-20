package com.linkon.domain.usecase.setting

import com.linkon.domain.TaskResult
import com.linkon.domain.model.setting.PrivacyPolicyModel
import kotlinx.coroutines.flow.Flow

interface GetPrivacyPolicyUseCase {
    operator fun invoke()
            : Flow<TaskResult<PrivacyPolicyModel>>
}