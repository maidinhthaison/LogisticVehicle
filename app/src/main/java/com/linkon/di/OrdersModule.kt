package com.linkon.di

import com.linkon.data.api.OrdersAPI
import com.linkon.data.repository.orders.OrdersRepositoryImpl
import com.linkon.data.retrofit.RetrofitManager
import com.linkon.data.usecase.orders.GetOrderDetailUseCaseImpl
import com.linkon.data.usecase.orders.GetListOrderUseCaseImpl
import com.linkon.data.usecase.orders.GetOrderTrajectoryUseCaseImpl
import com.linkon.domain.repository.orders.OrdersRepository
import com.linkon.domain.usecase.orders.GetOrderDetailUseCase
import com.linkon.domain.usecase.orders.GetListOrderUseCase
import com.linkon.domain.usecase.orders.GetOrderTrajectoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OrdersModule {

    @Singleton
    @Provides
    fun provideOrdersRepository(
        ordersAPI: OrdersAPI
    ): OrdersRepository {
        return OrdersRepositoryImpl(
            ordersAPI = ordersAPI
        )
    }

    @Singleton
    @Provides
    fun provideOrdersAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): OrdersAPI {
        return retrofitManager[OrdersAPI::class.java]
    }

    @Provides
    fun provideGetListOrderUseCase(ordersRepository: OrdersRepository): GetListOrderUseCase {
        return GetListOrderUseCaseImpl(ordersRepository)
    }

    @Provides
    fun provideGetOrderDetailUseCase(ordersRepository: OrdersRepository): GetOrderDetailUseCase {
        return GetOrderDetailUseCaseImpl(ordersRepository)
    }

    @Provides
    fun provideGetOrderTrajectoryUseCase(ordersRepository: OrdersRepository): GetOrderTrajectoryUseCase {
        return GetOrderTrajectoryUseCaseImpl(ordersRepository)
    }


}