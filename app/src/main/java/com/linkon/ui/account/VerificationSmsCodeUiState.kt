package com.linkon.ui.account

import com.linkon.base.BaseUIState
import com.linkon.domain.model.user.SmsVerificationModel

data class VerificationSmsCodeUiState (
    override val data: SmsVerificationModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIState() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIState {
        return this.copy(
            data = (data ?: this.data) as SmsVerificationModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}