package com.linkon.data

import com.linkon.domain.AppError
import com.linkon.domain.TaskResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object SafeCallAPI {
    /**
     * Handle only response for retrofit client
     */
    suspend fun <T> callApi(
        api: suspend () -> Response<T>
    ): TaskResult<T> {
        return try {
            val response = api.invoke()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                TaskResult.Success(body)
            } else {
                throw HttpException(response)
            }
        } catch (throwable: Throwable) {
            val error = when (throwable) {
                is HttpException -> {
                    val response = throwable.response()
                    response?.errorBody()?.string()?.let {
                        APIResponseErrorDto.fromJson(it)
                    }?.let {
                        val error = AppError.ResponseError(
                            httpCode = it.code,
                            message = it.msg ?: ""
                        )
                        error
                    } ?: AppError.NetworkError(
                        httpCode = response?.code(),
                        message = response?.message() ?: throwable.localizedMessage
                    )
                }

                is IOException -> {
                    AppError.NetworkError(
                        message = throwable.localizedMessage ?: "IOException"
                    )
                }

                else -> {
                    AppError.GeneralError(throwable)
                }

            }
            TaskResult.Failure(error)
        }
    }
}