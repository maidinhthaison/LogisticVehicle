package com.linkon.data.usecase.orders

import com.linkon.domain.TaskResult
import com.linkon.domain.model.order.trajectory.OrderTrajectoryModel
import com.linkon.domain.repository.orders.OrdersRepository
import com.linkon.domain.usecase.orders.GetOrderTrajectoryUseCase
import kotlinx.coroutines.flow.Flow

class GetOrderTrajectoryUseCaseImpl (private val ordersRepository: OrdersRepository) :
    GetOrderTrajectoryUseCase {
    override fun invoke(
        orderNo: String
    ): Flow<TaskResult<OrderTrajectoryModel>> {
        return ordersRepository.getOrderTrajectory(orderNo = orderNo)
    }
}