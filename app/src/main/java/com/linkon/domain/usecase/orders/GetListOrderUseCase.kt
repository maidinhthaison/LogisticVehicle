package com.linkon.domain.usecase.orders

import com.linkon.domain.TaskResult
import com.linkon.domain.model.order.OrderModel
import kotlinx.coroutines.flow.Flow

interface GetListOrderUseCase {
    operator fun invoke(orderNo: String? = null,
                        pageNum: String? = null, pageSize: String? = null,
                        orderStatus: String? = null)
            : Flow<TaskResult<OrderModel>>
}