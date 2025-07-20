package com.linkon.data.repository.setting

import com.linkon.data.SafeCallAPI
import com.linkon.data.api.SettingAPI
import com.linkon.domain.TaskResult
import com.linkon.domain.map
import com.linkon.domain.model.setting.AboutUsModel
import com.linkon.domain.model.setting.HelpModel
import com.linkon.domain.model.setting.PrivacyPolicyModel
import com.linkon.domain.model.setting.TermOfUserModel
import com.linkon.domain.repository.SettingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SettingRepositoryImpl (private val settingAPI: SettingAPI,
                             private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : SettingRepository {

    override fun getTermOfUse(): Flow<TaskResult<TermOfUserModel>> = flow{
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            settingAPI.getTermOfUse()
        }.map { it.toTermOfUserModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getPrivacyPolicy(): Flow<TaskResult<PrivacyPolicyModel>> = flow{
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            settingAPI.getPrivacyPolicy()
        }.map { it.toPrivacyPolicyModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getAboutUs(): Flow<TaskResult<AboutUsModel>> = flow{
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            settingAPI.getAboutUs()
        }.map { it.toAboutUsModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getHelp(): Flow<TaskResult<HelpModel>> = flow{
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            settingAPI.getHelpCenter()
        }.map { it.toHelpModel() }
        emit(result)
    }.flowOn(defaultDispatcher)
}