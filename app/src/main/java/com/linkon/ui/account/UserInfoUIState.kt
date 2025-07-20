package com.linkon.ui.account

import com.linkon.base.BaseUIState
import com.linkon.domain.model.user.UserInfoModel

data class UserInfoUIState (
    override val data: UserInfoModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIState() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIState {
        return this.copy(
            data = (data ?: this.data) as UserInfoModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}