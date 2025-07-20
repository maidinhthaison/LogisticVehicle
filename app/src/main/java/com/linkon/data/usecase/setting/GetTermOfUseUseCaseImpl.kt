package com.linkon.data.usecase.setting

import com.linkon.domain.TaskResult
import com.linkon.domain.model.setting.TermOfUserModel
import com.linkon.domain.repository.SettingRepository
import com.linkon.domain.usecase.setting.GetTermOfUseUseCase
import kotlinx.coroutines.flow.Flow

class GetTermOfUseUseCaseImpl (private val settingRepository: SettingRepository) :
    GetTermOfUseUseCase {
    override fun invoke(): Flow<TaskResult<TermOfUserModel>> {
        return  settingRepository.getTermOfUse()
    }
}