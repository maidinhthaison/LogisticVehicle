package com.linkon.ui.document

import com.linkon.base.BaseUIState
import com.linkon.domain.model.document.DocumentModel

data class UploadShippingOrderUiState (
    override val data: DocumentModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIState() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIState {
        return this.copy(
            data = (data ?: this.data) as DocumentModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}