package com.linkon.data.api.response.orders.trajectory

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.order.trajectory.OrderTrajectoryModel
import java.io.Serializable

@Keep
data class OrderTrajectoryResponseDto  (
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: List<OrderDataTrajectoryResponseDto>,
): Serializable {
    fun toModel(): OrderTrajectoryModel {
        return OrderTrajectoryModel(
            code = code,
            msg = msg,
            data = data.map { it.toModel() }
        )
    }
}
