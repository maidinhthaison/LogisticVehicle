package com.linkon.data.usecase.document

import com.linkon.data.api.request.UploadShippingOrderRequestDto
import com.linkon.domain.TaskResult
import com.linkon.domain.model.document.DocumentModel
import com.linkon.domain.repository.document.DocumentRepository
import com.linkon.domain.usecase.document.UploadShippingOrderUseCase
import kotlinx.coroutines.flow.Flow

class UploadShippingOrderUseCaseImp (private val documentRepository: DocumentRepository) :
    UploadShippingOrderUseCase {

    override fun invoke(uploadShippingOrderRequestDto: UploadShippingOrderRequestDto):
            Flow<TaskResult<DocumentModel>> {
        return documentRepository.uploadShippingOrder(uploadShippingOrderRequestDto)
    }
}