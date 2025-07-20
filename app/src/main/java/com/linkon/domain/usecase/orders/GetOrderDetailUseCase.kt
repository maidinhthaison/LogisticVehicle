package com.linkon.domain.usecase.orders

import com.linkon.domain.TaskResult
import com.linkon.domain.model.order.detail.OrderDetailModel
import kotlinx.coroutines.flow.Flow

interface GetOrderDetailUseCase {
    operator fun invoke(orderNo: String)
            : Flow<TaskResult<OrderDetailModel>>
}