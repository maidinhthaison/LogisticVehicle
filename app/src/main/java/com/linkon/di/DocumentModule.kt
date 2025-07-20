package com.linkon.di

import com.linkon.data.api.DocumentAPI
import com.linkon.data.repository.document.DocumentRepositoryImpl
import com.linkon.data.retrofit.RetrofitManager
import com.linkon.data.usecase.document.UploadShippingOrderUseCaseImp
import com.linkon.domain.repository.document.DocumentRepository
import com.linkon.domain.usecase.document.UploadShippingOrderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DocumentModule {

    @Singleton
    @Provides
    fun provideDocumentRepository(
        documentAPI: DocumentAPI
    ): DocumentRepository {
        return DocumentRepositoryImpl(
            documentAPI = documentAPI
        )
    }

    @Singleton
    @Provides
    fun provideDocumentAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): DocumentAPI {
        return retrofitManager[DocumentAPI::class.java]
    }

    @Provides
    fun provideUploadShippingOrderUseCase(documentRepository: DocumentRepository):
            UploadShippingOrderUseCase {
        return UploadShippingOrderUseCaseImp(documentRepository)
    }
}