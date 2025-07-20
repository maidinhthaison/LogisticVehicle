package com.linkon.data.api.response.document

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.document.DocumentDataModel
import com.linkon.domain.model.document.DocumentModel
import java.io.Serializable

@Keep
class UploadDocumentResponseDto(
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: DocumentDataResponseDto?,
) : Serializable {
    fun toDocumentModel(): DocumentModel {
        return DocumentModel(
            code = code,
            msg = msg,
            data = data?.toDocumentDataModel()
        )
    }
}

@Keep
class DocumentDataResponseDto(
    @SerializedName("url") val url: String?,
    @SerializedName("fileName") val msg: String?,
    @SerializedName("ossId") val ossId: String?,
) : Serializable {
    fun toDocumentDataModel(): DocumentDataModel {
        return DocumentDataModel(
            url = url,
            fileName = msg,
            ossId = ossId
        )
    }
}