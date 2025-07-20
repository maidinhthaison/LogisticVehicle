package com.linkon.data.api.response.orders

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.order.OrderListItemModel
import com.linkon.domain.model.order.OrderListModel
import com.linkon.domain.model.order.OrderListPagingModel
import java.io.Serializable

@Keep
data class OrderDataResponseDto (
    @SerializedName("items") val items: List<OrderListItemResponseDto>?,
    @SerializedName("pagination") val pagination: OrderListPagingResponseDto?
): Serializable {
    fun toOrderListModel(): OrderListModel {
        return OrderListModel(
            items = items?.map { it.toOrderListItemModel() },
            pagination = pagination?.toOrderListPagingModel()
        )
    }
}

@Keep
data class OrderListItemResponseDto (
    @SerializedName("orderId") val orderId: String?,
    @SerializedName("orderNo") val orderNo: String?,
    @SerializedName("transportFee") val transportFee: String?,
    @SerializedName("orderStatus") val orderStatus: String?,
    @SerializedName("orderStatusName") val orderStatusName: String?,
    @SerializedName("factoryName") val factoryName: String?,
    @SerializedName("factoryAddress") val factoryAddress: String?,
    @SerializedName("receiverAddress") val receiverAddress: String?,
    @SerializedName("vehicleType") val vehicleType: String?,
    @SerializedName("transportStartTime") val transportStartTime: String?,
    @SerializedName("transportArrivalTime") val transportArrivalTime: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("vehicleNo") val vehicleNo: String?,
    @SerializedName("deliveryDate") val deliveryDate: String?,
    @SerializedName("goodsName") val goodsName: String?

): Serializable{
    fun toOrderListItemModel(): OrderListItemModel {
        return OrderListItemModel(
            orderId = orderId,
            orderNo = orderNo,
            transportFee = transportFee,
            orderStatus = orderStatus,
            orderStatusName = orderStatusName,
            factoryName = factoryName,
            factoryAddress = factoryAddress,
            receiverAddress = receiverAddress,
            vehicleType = vehicleType,
            transportStartTime = transportStartTime,
            transportArrivalTime = transportArrivalTime,
            createdAt = createdAt,
            vehicleNo = vehicleNo,
            deliveryDate = deliveryDate,
            goodsName = goodsName
        )
    }
}

@Keep
data class OrderListPagingResponseDto (
    @SerializedName("currentPage") val currentPage: Int?,
    @SerializedName("itemsPerPage") val itemsPerPage: Int?,
    @SerializedName("countItem") val countItem: Int?
){
    fun toOrderListPagingModel(): OrderListPagingModel {
        return OrderListPagingModel(
            currentPage = currentPage,
            itemsPerPage = itemsPerPage,
            countItem = countItem)

    }
}