package com.linkon.ui.orders.trajectory

import com.linkon.base.BaseUIState
import com.linkon.domain.model.order.trajectory.OrderTrajectoryModel

data class OrderTrajectoryUiState (
    override val data: OrderTrajectoryModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIState() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIState {
        return this.copy(
            data = (data ?: this.data) as OrderTrajectoryModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}