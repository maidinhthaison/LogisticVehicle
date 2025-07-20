package com.linkon.domain.usecase.user

import com.linkon.domain.TaskResult
import com.linkon.domain.model.user.UserInfoModel
import kotlinx.coroutines.flow.Flow

interface GetUserInfoUseCase {
    operator fun invoke()
            : Flow<TaskResult<UserInfoModel>>
}