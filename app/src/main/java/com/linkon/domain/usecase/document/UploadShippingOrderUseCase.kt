package com.linkon.domain.usecase.document

import com.linkon.data.api.request.UploadShippingOrderRequestDto
import com.linkon.domain.TaskResult
import com.linkon.domain.model.document.DocumentModel
import kotlinx.coroutines.flow.Flow

interface UploadShippingOrderUseCase {
    operator fun invoke(uploadShippingOrderRequestDto: UploadShippingOrderRequestDto)
            : Flow<TaskResult<DocumentModel>>
}