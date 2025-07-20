package com.linkon.ui.orders.detail

import com.linkon.base.BaseUIState
import com.linkon.domain.model.order.detail.OrderDetailModel

data class OrderDetailUiState (
    override val data: OrderDetailModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIState() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIState {
        return this.copy(
            data = (data ?: this.data) as OrderDetailModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}