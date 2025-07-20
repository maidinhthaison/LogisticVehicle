package com.linkon.data.api.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UploadShippingOrderRequestDto (
    @SerializedName("file") var uri: String?
) : Serializable