package com.linkon.data.api.response.setting

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.setting.HelpItemModel
import com.linkon.domain.model.setting.HelpModel
import java.io.Serializable

@Keep
class HelpResponseDto(
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: List<HelpItemResponseDto>?,
) : Serializable {
    fun toHelpModel(): HelpModel {
        return HelpModel(
            code = code,
            msg = msg,
            dataResponse = data?.map { it.toHelpItemModel() }
        )
    }
}

@Keep
class HelpItemResponseDto(
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: String?
) : Serializable {
    fun toHelpItemModel(): HelpItemModel {
        return HelpItemModel(
            title = title,
            content = content
        )
    }
}