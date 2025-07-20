package com.linkon.di

import com.linkon.data.api.SettingAPI
import com.linkon.data.repository.setting.SettingRepositoryImpl
import com.linkon.data.retrofit.RetrofitManager
import com.linkon.data.usecase.setting.GetAboutUsUseCaseImpl
import com.linkon.data.usecase.setting.GetHelpUseCaseImpl
import com.linkon.data.usecase.setting.GetPrivacyPolicyUseCaseImpl
import com.linkon.data.usecase.setting.GetTermOfUseUseCaseImpl
import com.linkon.domain.repository.SettingRepository
import com.linkon.domain.usecase.setting.GetAboutUsUseCase
import com.linkon.domain.usecase.setting.GetHelpUseCase
import com.linkon.domain.usecase.setting.GetPrivacyPolicyUseCase
import com.linkon.domain.usecase.setting.GetTermOfUseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingModule {

    @Singleton
    @Provides
    fun provideSettingRepository(
        settingAPI: SettingAPI
    ): SettingRepository {
        return SettingRepositoryImpl(
            settingAPI = settingAPI
        )
    }

    @Singleton
    @Provides
    fun provideSettingAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): SettingAPI {
        return retrofitManager[SettingAPI::class.java]
    }

    @Provides
    fun provideGetTermOfUseUseCase(settingRepository: SettingRepository): GetTermOfUseUseCase {
        return GetTermOfUseUseCaseImpl(settingRepository)
    }

    @Provides
    fun provideGetPrivacyPolicyUseCase(settingRepository: SettingRepository): GetPrivacyPolicyUseCase {
        return GetPrivacyPolicyUseCaseImpl(settingRepository)
    }

    @Provides
    fun provideGetAboutUsUseCase(settingRepository: SettingRepository): GetAboutUsUseCase {
        return GetAboutUsUseCaseImpl(settingRepository)
    }

    @Provides
    fun provideGetHelpUseCase(settingRepository: SettingRepository): GetHelpUseCase {
        return GetHelpUseCaseImpl(settingRepository)
    }

}