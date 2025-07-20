package com.linkon.ui.orders

import com.linkon.base.BaseUIState
import com.linkon.domain.model.order.OrderModel

data class OrderListUiState (
    override val data: OrderModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIState() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIState {
        return this.copy(
            data = (data ?: this.data) as OrderModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}
