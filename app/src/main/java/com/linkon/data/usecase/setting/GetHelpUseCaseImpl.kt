package com.linkon.data.usecase.setting

import com.linkon.domain.TaskResult
import com.linkon.domain.model.setting.HelpModel
import com.linkon.domain.repository.SettingRepository
import com.linkon.domain.usecase.setting.GetHelpUseCase
import kotlinx.coroutines.flow.Flow

class GetHelpUseCaseImpl (private val settingRepository: SettingRepository) :
    GetHelpUseCase {
    override fun invoke(): Flow<TaskResult<HelpModel>> {
        return  settingRepository.getHelp()
    }
}