package com.linkon.data.api.response.orders.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.order.detail.OrderDetailModel
import java.io.Serializable

@Keep
data class OrderDetailResponseDto (
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: OrderDetailDataResponseDto,
): Serializable {
    fun toOrderDetailDataModel(): OrderDetailModel {
        return OrderDetailModel(
            code = code,
            msg = msg,
            data = data.toOrderDetailItemModel()
        )
    }
}