package com.linkon.data.api.response.orders.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.order.detail.ActivityLogImageModel
import com.linkon.domain.model.order.detail.ActivityLogModel
import com.linkon.domain.model.order.detail.DriverInfoModel
import com.linkon.domain.model.order.detail.OrderDetailItemModel
import com.linkon.domain.model.order.detail.ShippingReceiptImgModel
import java.io.Serializable

@Keep
data class OrderDetailDataResponseDto(
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
    @SerializedName("vehicleNo") val vehicleNo: String?,


    @SerializedName("driverInfo") val driverInfo: DriverInfoDto?,
    @SerializedName("goodsType") val goodsType: String?,
    @SerializedName("paymentMethod") val paymentMethod: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("shippingReceiptImgs") val shippingReceiptImgs: List<ShippingReceiptImgDto>?,
    @SerializedName("activityLog") val activityLog: List<ActivityLogDto>?
) : Serializable {

    fun toOrderDetailItemModel(): OrderDetailItemModel {
        return OrderDetailItemModel(
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
            vehicleNo = vehicleNo,
            driverInfo = driverInfo?.toDriverInfoModel(),
            goodsType = goodsType,
            paymentMethod = paymentMethod,
            createdAt = createdAt,
            shippingReceiptImgs = shippingReceiptImgs?.map { it.toShippingReceiptImgModel() },
            activityLog = activityLog?.map { it.toActivityLogModel() }
        )
    }
}

@Keep
data class DriverInfoDto(
    @SerializedName("driverId") val driverId: String?,
    @SerializedName("driverName") val driverName: String?,
    @SerializedName("driverPhone") val driverPhone: String?,
    @SerializedName("driverAvatar") val driverAvatar: String?
) : Serializable {
    fun toDriverInfoModel(): DriverInfoModel {
        return DriverInfoModel(
            driverId = driverId,
            driverName = driverName,
            driverPhone = driverPhone
        )
    }
}

@Keep
data class ShippingReceiptImgDto(
    @SerializedName("imgUrl") val imgUrl: String?,
    @SerializedName("uploadTime") val uploadTime: String?
) : Serializable {
    fun toShippingReceiptImgModel(): ShippingReceiptImgModel {
        return ShippingReceiptImgModel(
            imgUrl = imgUrl,
            uploadTime = uploadTime
        )
    }
}

@Keep
data class ActivityLogDto(
    @SerializedName("logTime") val logTime: String?,
    @SerializedName("action") val action: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("images") val images: List<ActivityLogImageDto>?
) : Serializable {
    fun toActivityLogModel(): ActivityLogModel {
        return ActivityLogModel(
            logTime = logTime,
            action = action,
            description = description,
            images = images?.map { it.toActivityLogImageModel() }
        )
    }
}

@Keep
data class ActivityLogImageDto(
    @SerializedName("imgUrl") val imgUrl: String?,
    @SerializedName("uploadTime") val uploadTime: String?
) : Serializable {
    fun toActivityLogImageModel(): ActivityLogImageModel {
        return ActivityLogImageModel(
            imgUrl = imgUrl,
            uploadTime = uploadTime
        )
    }
}
