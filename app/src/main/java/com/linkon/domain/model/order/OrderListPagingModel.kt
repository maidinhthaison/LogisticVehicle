package com.linkon.domain.model.order

data class OrderListPagingModel(
    val currentPage: Int? = null,
    val itemsPerPage: Int? = null,
    val countItem: Int? = null
)