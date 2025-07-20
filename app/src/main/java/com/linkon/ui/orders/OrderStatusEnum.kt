package com.linkon.ui.orders

enum class OrderStatusEnum(val value: String) {
    PENDING("pending"),
    ACCEPTED("Accepted"),
    QUEUED("Queued"),
    SHIPPED("Shipped"),
    COMPLETED("Completed"),
    CANCELED("Cancelled"),
}