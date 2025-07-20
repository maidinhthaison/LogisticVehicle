package com.linkon.domain.usecase.setting

import com.linkon.domain.TaskResult
import com.linkon.domain.model.setting.AboutUsModel
import kotlinx.coroutines.flow.Flow

interface GetAboutUsUseCase {
    operator fun invoke()
            : Flow<TaskResult<AboutUsModel>>
}