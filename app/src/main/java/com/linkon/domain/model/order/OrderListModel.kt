package com.linkon.domain.model.order


data class OrderListModel(
    val items: List<OrderListItemModel>? = null,
    val pagination: OrderListPagingModel? = null
)