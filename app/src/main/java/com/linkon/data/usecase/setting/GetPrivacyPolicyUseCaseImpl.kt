package com.linkon.data.usecase.setting

import com.linkon.domain.TaskResult
import com.linkon.domain.model.setting.PrivacyPolicyModel
import com.linkon.domain.repository.SettingRepository
import com.linkon.domain.usecase.setting.GetPrivacyPolicyUseCase
import kotlinx.coroutines.flow.Flow

class GetPrivacyPolicyUseCaseImpl (private val settingRepository: SettingRepository) :
    GetPrivacyPolicyUseCase {
    override fun invoke(): Flow<TaskResult<PrivacyPolicyModel>> {
        return  settingRepository.getPrivacyPolicy()
    }
}