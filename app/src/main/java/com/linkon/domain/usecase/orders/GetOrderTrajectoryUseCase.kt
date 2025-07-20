package com.linkon.domain.usecase.orders

import com.linkon.domain.TaskResult
import com.linkon.domain.model.order.trajectory.OrderTrajectoryModel
import kotlinx.coroutines.flow.Flow

interface GetOrderTrajectoryUseCase {
    operator fun invoke(orderNo: String)
            : Flow<TaskResult<OrderTrajectoryModel>>
}