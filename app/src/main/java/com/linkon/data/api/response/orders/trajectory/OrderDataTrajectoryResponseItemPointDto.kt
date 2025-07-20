package com.linkon.data.api.response.orders.trajectory

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.order.trajectory.OrderDataTrajectoryItemPointModel
import java.io.Serializable

@Keep
data class OrderDataTrajectoryResponseItemPointDto(
    @SerializedName("lat") val lat: Float?,
    @SerializedName("lng") val lng: Float?,
    @SerializedName("timestamp") val timestamp: String?,
    @SerializedName("speed") val speed: Float?,
) : Serializable {
    fun toModel(): OrderDataTrajectoryItemPointModel {
        return OrderDataTrajectoryItemPointModel(
            lat = lat,
            lng = lng,
            timestamp = timestamp,
            speed = speed
        )
    }
}