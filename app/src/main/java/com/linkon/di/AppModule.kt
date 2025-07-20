package com.linkon.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.linkon.data.local.LocalCacheImpl
import com.linkon.data.network.ConnectivityDataSource
import com.linkon.data.retrofit.RetrofitManager
import com.linkon.domain.local.LocalCache
import com.linkon.domain.local.UserAppSession
import com.linkon.utils.APP_SESSION_PREFS_SECURE
import com.linkon.utils.FileUtils
import com.linkon.utils.FileUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideConnectivityDataSource(
        @ApplicationContext context: Context
    ): ConnectivityDataSource {
        return ConnectivityDataSource(
            applicationContext = context
        )
    }

    @DefaultApiQualifier
    @Provides
    fun provideRetrofitManager(
        gson: Gson, connectivityDataSource: ConnectivityDataSource,
        userAppSession: UserAppSession
    ): RetrofitManager {
        return RetrofitManager(
            gson = gson,
            connectivityDataSource = connectivityDataSource,
            userAppSession = userAppSession
        )
    }

    // File Utils
    @Provides
    @Singleton
    fun provideFileUtils(
        @ApplicationContext context: Context
    ): FileUtils {
        return FileUtilsImpl(
            context = context
        )
    }

    // Local Cache
    @Singleton
    @Provides
    fun provideAppSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return LocalCacheImpl.getSharedPreferences(context, APP_SESSION_PREFS_SECURE)
    }

    @Singleton
    @Provides
    fun provideLocalCache(sharedPreferences: SharedPreferences): LocalCache {
        return LocalCacheImpl(sharedPreferences)
    }

    // Glide
    @Provides
    @Singleton
    fun provideGlideModule(
    ): GlideModule { return GlideModule()
    }
}