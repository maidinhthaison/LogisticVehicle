package com.linkon.di

import com.linkon.data.api.UserAPI
import com.linkon.data.local.UserAppSessionImpl
import com.linkon.data.repository.user.UserRepositoryImpl
import com.linkon.data.retrofit.RetrofitManager
import com.linkon.data.usecase.user.GetUserInfoUseCaseImpl
import com.linkon.data.usecase.user.LoginUseCaseImpl
import com.linkon.data.usecase.user.LogoutUseCaseImpl
import com.linkon.data.usecase.user.RegisterUseCaseCaseImpl
import com.linkon.data.usecase.user.VerifySmsCodeUseCaseImpl
import com.linkon.domain.local.LocalCache
import com.linkon.domain.local.UserAppSession
import com.linkon.domain.repository.user.UserRepository
import com.linkon.domain.usecase.user.GetUserInfoUseCase
import com.linkon.domain.usecase.user.LoginUseCase
import com.linkon.domain.usecase.user.LogoutUseCase
import com.linkon.domain.usecase.user.RegisterUseCase
import com.linkon.domain.usecase.user.VerifySmsCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Singleton
    @Provides
    fun provideUserAppSession(cache: LocalCache): UserAppSession {
        return UserAppSessionImpl(cache)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        userAPI: UserAPI
    ): UserRepository {
        return UserRepositoryImpl(
            userAPI = userAPI
        )
    }

    @Singleton
    @Provides
    fun provideUserAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): UserAPI {
        return retrofitManager[UserAPI::class.java]
    }

    @Provides
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase {
        return LoginUseCaseImpl(userRepository)
    }

    @Provides
    fun provideRegisterUseCase(userRepository: UserRepository): RegisterUseCase {
        return RegisterUseCaseCaseImpl(userRepository)
    }

    @Provides
    fun provideGetUserInfoUseCase(userRepository: UserRepository): GetUserInfoUseCase {
        return GetUserInfoUseCaseImpl(userRepository)
    }

    @Provides
    fun provideVerificationSmsCodeUseCase(userRepository: UserRepository): VerifySmsCodeUseCase {
        return VerifySmsCodeUseCaseImpl(userRepository)
    }

    @Provides
    fun provideLogoutUseCase(userRepository: UserRepository): LogoutUseCase {
        return LogoutUseCaseImpl(userRepository)
    }


}