package com.linkon.data.usecase.orders

import com.linkon.domain.TaskResult
import com.linkon.domain.model.order.OrderModel
import com.linkon.domain.repository.orders.OrdersRepository
import com.linkon.domain.usecase.orders.GetListOrderUseCase
import kotlinx.coroutines.flow.Flow

class GetListOrderUseCaseImpl (private val ordersRepository: OrdersRepository) :
    GetListOrderUseCase {
    override fun invoke(
        orderNo: String?,
        pageNum: String?,
        pageSize: String?,
        orderStatus: String?
    ): Flow<TaskResult<OrderModel>> {
        return ordersRepository.getListOrders(orderNo = orderNo,
            pageNum = pageNum,
            pageSize = pageSize,
            orderStatus = orderStatus)
    }
}