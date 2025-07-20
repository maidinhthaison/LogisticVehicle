package com.linkon.data.usecase.orders

import com.linkon.domain.TaskResult
import com.linkon.domain.model.order.detail.OrderDetailModel
import com.linkon.domain.repository.orders.OrdersRepository
import com.linkon.domain.usecase.orders.GetOrderDetailUseCase
import kotlinx.coroutines.flow.Flow

class GetOrderDetailUseCaseImpl (private val ordersRepository: OrdersRepository) :
    GetOrderDetailUseCase {
    override fun invoke(
        orderNo: String
    ): Flow<TaskResult<OrderDetailModel>> {
        return ordersRepository.getOrderDetail(orderNo = orderNo)
    }
}