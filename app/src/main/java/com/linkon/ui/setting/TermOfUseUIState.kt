package com.linkon.ui.setting

import com.linkon.base.BaseUIState
import com.linkon.domain.model.setting.TermOfUserModel

data class TermOfUseUIState  (
    override val data: TermOfUserModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIState() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIState {
        return this.copy(
            data = (data ?: this.data) as TermOfUserModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}