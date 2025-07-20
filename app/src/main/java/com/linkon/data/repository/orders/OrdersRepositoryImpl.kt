package com.linkon.data.repository.orders

import com.linkon.data.SafeCallAPI
import com.linkon.data.api.OrdersAPI
import com.linkon.domain.TaskResult
import com.linkon.domain.map
import com.linkon.domain.model.order.OrderModel
import com.linkon.domain.model.order.detail.OrderDetailModel
import com.linkon.domain.model.order.trajectory.OrderTrajectoryModel
import com.linkon.domain.repository.orders.OrdersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OrdersRepositoryImpl (private val ordersAPI: OrdersAPI,
                            private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO) :
    OrdersRepository {

    override fun getListOrders(
        orderNo: String?,
        pageNum: String?,
        pageSize: String?,
        orderStatus: String?
    ): Flow<TaskResult<OrderModel>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            ordersAPI.getListOrders(orderNo = orderNo,pageNum = pageNum,pageSize = pageSize,
                orderStatus = orderStatus)
        }.map { it.toOrderDataModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getOrderDetail(orderNo: String): Flow<TaskResult<OrderDetailModel>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            ordersAPI.getOrderDetail(orderNo)
        }.map { it.toOrderDetailDataModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getOrderTrajectory(orderNo: String): Flow<TaskResult<OrderTrajectoryModel>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            ordersAPI.getOrderTrajectory(orderNo)
        }.map { it.toModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

}