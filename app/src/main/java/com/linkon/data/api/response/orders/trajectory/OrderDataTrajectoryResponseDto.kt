package com.linkon.data.api.response.orders.trajectory

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.order.trajectory.OrderDataTrajectoryItemModel
import java.io.Serializable

@Keep
data class OrderDataTrajectoryResponseDto  (
    @SerializedName("distance") val distance: Float?,
    @SerializedName("duration") val duration: Float?,
    @SerializedName("points") val points: List<OrderDataTrajectoryResponseItemPointDto>?,
): Serializable {
    fun toModel(): OrderDataTrajectoryItemModel {
        return OrderDataTrajectoryItemModel(
            distance = distance,
            duration = duration,
            points = points?.map { it.toModel() }
        )
    }
}