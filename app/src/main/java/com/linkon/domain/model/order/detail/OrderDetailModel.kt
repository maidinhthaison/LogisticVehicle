package com.linkon.domain.model.order.detail

data class OrderDetailModel (
    val code : Int? = null,
    val msg : String? = null,
    val data : OrderDetailItemModel? = null
)