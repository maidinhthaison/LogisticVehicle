package com.linkon.data.api

import com.linkon.data.api.request.UploadShippingOrderRequestDto
import com.linkon.data.api.response.document.UploadDocumentResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DocumentAPI {
    @Multipart
    @POST("app/resource/oss/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
    ): Response<UploadDocumentResponseDto>
}