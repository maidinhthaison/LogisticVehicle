package com.linkon.domain.model.order.trajectory

data class OrderDataTrajectoryItemModel (
    val distance : Float? = null,
    val duration : Float? = null,
    val points : List<OrderDataTrajectoryItemPointModel>? = null
)