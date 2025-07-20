package com.linkon.domain.repository

import com.linkon.domain.TaskResult
import com.linkon.domain.model.setting.AboutUsModel
import com.linkon.domain.model.setting.HelpModel
import com.linkon.domain.model.setting.PrivacyPolicyModel
import com.linkon.domain.model.setting.TermOfUserModel
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getTermOfUse(): Flow<TaskResult<TermOfUserModel>>
    fun getPrivacyPolicy(): Flow<TaskResult<PrivacyPolicyModel>>
    fun getAboutUs(): Flow<TaskResult<AboutUsModel>>
    fun getHelp(): Flow<TaskResult<HelpModel>>
}