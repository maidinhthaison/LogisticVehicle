package com.linkon.data.repository.document

import android.webkit.MimeTypeMap
import com.linkon.data.SafeCallAPI
import com.linkon.data.api.DocumentAPI
import com.linkon.data.api.request.UploadShippingOrderRequestDto
import com.linkon.domain.TaskResult
import com.linkon.domain.map
import com.linkon.domain.model.document.DocumentModel
import com.linkon.domain.repository.document.DocumentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class DocumentRepositoryImpl (private val documentAPI: DocumentAPI,
                              private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : DocumentRepository {

    override fun uploadShippingOrder(requestDto: UploadShippingOrderRequestDto):
            Flow<TaskResult<DocumentModel>> = flow{
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            val file = File(requestDto.uri.toString())
            val extension = file.extension
            val mimeType = MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(extension.lowercase()) ?: "application/octet-stream"
            val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
            documentAPI.uploadImage(filePart)
        }.map { it.toDocumentModel() }
        emit(result)
    }.flowOn(defaultDispatcher)

}