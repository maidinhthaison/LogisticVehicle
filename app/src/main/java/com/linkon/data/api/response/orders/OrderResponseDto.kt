package com.linkon.data.api.response.orders

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.order.OrderModel
import java.io.Serializable

@Keep
data class OrderResponseDto  (
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: OrderDataResponseDto,
): Serializable {
    fun toOrderDataModel(): OrderModel {
        return OrderModel(
            code = code,
            msg = msg,
            dataResponse = data.toOrderListModel()
        )
    }
}


