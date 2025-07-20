package com.linkon.ui.setting

import com.linkon.base.BaseUIState
import com.linkon.domain.model.setting.AboutUsModel

data class AboutUsUIState (
    override val data: AboutUsModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIState() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIState {
        return this.copy(
            data = (data ?: this.data) as AboutUsModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}