package com.linkon.domain.model.order.trajectory

data class OrderTrajectoryModel(
    val code: Int? = null,
    val msg: String? = null,
    val data: List<OrderDataTrajectoryItemModel>? = null
)