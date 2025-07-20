package com.linkon.domain.repository.document

import com.linkon.data.api.request.UploadShippingOrderRequestDto
import com.linkon.domain.TaskResult
import com.linkon.domain.model.document.DocumentModel
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {
    fun uploadShippingOrder(requestDto: UploadShippingOrderRequestDto): Flow<TaskResult<DocumentModel>>
}