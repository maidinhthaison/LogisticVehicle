package com.linkon.domain.repository.orders

import com.linkon.domain.TaskResult
import com.linkon.domain.model.order.OrderModel
import com.linkon.domain.model.order.detail.OrderDetailModel
import com.linkon.domain.model.order.trajectory.OrderTrajectoryModel
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    fun getListOrders(orderNo: String? = null, pageNum: String? = null,
                      pageSize: String? = null, orderStatus: String? = null): Flow<TaskResult<OrderModel>>
    fun getOrderDetail(orderNo: String): Flow<TaskResult<OrderDetailModel>>
    fun getOrderTrajectory(orderNo: String): Flow<TaskResult<OrderTrajectoryModel>>
}