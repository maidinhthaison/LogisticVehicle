package com.linkon.data.api

import com.linkon.data.api.response.orders.detail.OrderDetailResponseDto
import com.linkon.data.api.response.orders.OrderResponseDto
import com.linkon.data.api.response.orders.trajectory.OrderTrajectoryResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersAPI {
    @GET("app/order/lists")
    suspend fun getListOrders(
        @Query("orderNo") orderNo: String?,
        @Query("pageNum") pageNum: String?,
        @Query("pageSize") pageSize: String?,
        @Query("orderStatus") orderStatus: String?
    ): Response<OrderResponseDto>

    @GET("app/order/detail")
    suspend fun getOrderDetail(
        @Query("orderNo") orderNo: String?
    ): Response<OrderDetailResponseDto>

    @GET("app/order/gps")
    suspend fun getOrderTrajectory(
        @Query("orderNo") orderNo: String?
    ): Response<OrderTrajectoryResponseDto>
}