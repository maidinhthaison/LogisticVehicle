package com.linkon.domain.model.order.detail

data class OrderDetailItemModel(
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
    val vehicleNo: String? = null,

    val driverInfo: DriverInfoModel? = null,
    val goodsType: String? = null,
    val paymentMethod: String? = null,
    val createdAt: String? = null,
    val shippingReceiptImgs: List<ShippingReceiptImgModel>? = null,
    val activityLog: List<ActivityLogModel>? = null
)