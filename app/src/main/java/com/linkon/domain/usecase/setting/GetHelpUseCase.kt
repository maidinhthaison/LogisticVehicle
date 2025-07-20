package com.linkon.domain.usecase.setting

import com.linkon.domain.TaskResult
import com.linkon.domain.model.setting.HelpModel
import kotlinx.coroutines.flow.Flow


interface GetHelpUseCase {
    operator fun invoke()
            : Flow<TaskResult<HelpModel>>
}