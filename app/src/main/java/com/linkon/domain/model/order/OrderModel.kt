package com.linkon.domain.model.order

data class OrderModel(
    val code : Int? = null,
    val msg : String? = null,
    val dataResponse : OrderListModel? = null
)