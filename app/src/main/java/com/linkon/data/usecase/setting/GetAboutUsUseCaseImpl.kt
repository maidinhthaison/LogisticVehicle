package com.linkon.data.usecase.setting

import com.linkon.domain.TaskResult
import com.linkon.domain.model.setting.AboutUsModel
import com.linkon.domain.repository.SettingRepository
import com.linkon.domain.usecase.setting.GetAboutUsUseCase
import kotlinx.coroutines.flow.Flow

class GetAboutUsUseCaseImpl (private val settingRepository: SettingRepository) :
    GetAboutUsUseCase {
    override fun invoke(): Flow<TaskResult<AboutUsModel>> {
        return  settingRepository.getAboutUs()
    }
}