package com.linkon.ui.orders.detail.adapter

import android.net.Uri

sealed class GridItem {
    data class UploadedItem(val imageUriPath: String, val imageName: String, val imageUri: Uri) : GridItem()
    data object AddAction : GridItem()
}