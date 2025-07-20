package com.linkon.domain.model.order


data class OrderListItemModel(
    val orderId: String? = null,
    val orderNo: String? = null,
    val transportFee: String? = null,
    val orderStatus: String? = null,
    val orderStatusName: String? = null,
    val factoryName: String? = null,
    val factoryAddress: String? = null,
    val receiverAddress: String? = null,
    val vehicleType: String? = null,
    val transportStartTime: String? = null,
    val transportArrivalTime: String? = null,
    val createdAt: String? = null,
    val vehicleNo: String? = null,
    val deliveryDate: String? = null,
    val goodsName: String? = null,
)
