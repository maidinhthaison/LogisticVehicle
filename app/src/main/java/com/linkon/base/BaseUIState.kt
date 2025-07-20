package com.linkon.base

import java.io.Serializable

abstract class BaseUIState : Serializable {
    open val data: Any? = null
    open val isLoading: Boolean = false
    open val messageId: Int? = null
    abstract fun copyWith(
        data: Any? = this.data,
        isLoading: Boolean = this.isLoading,
        messageId: Int? = this.messageId
    ): BaseUIState
}