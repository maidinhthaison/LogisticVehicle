package com.linkon.domain.model.order.detail

data class ActivityLogModel (
    val logTime: String? = null,
    val action: String? = null,
    val description: String? = null,
    val images: List<ActivityLogImageModel>? = null

)