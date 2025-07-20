package com.linkon.ui.setting

import com.linkon.base.BaseUIState
import com.linkon.domain.model.setting.PrivacyPolicyModel

data class PrivacyPolicyUIState  (
    override val data: PrivacyPolicyModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIState() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIState {
        return this.copy(
            data = (data ?: this.data) as PrivacyPolicyModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}