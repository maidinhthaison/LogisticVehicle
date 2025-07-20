package com.linkon.data.retrofit

import com.google.gson.Gson
import com.linkon.BuildConfig
import com.linkon.data.network.ConnectivityDataSource
import com.linkon.data.retrofit.interceptors.HttpLoggingInterceptor
import com.linkon.domain.local.UserAppSession
import com.linkon.utils.CLIENT_ID
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class RetrofitManager (
    private val gson: Gson,
    private val connectivityDataSource: ConnectivityDataSource,
    private val userAppSession: UserAppSession
) {

    class NetworkLogger : HttpLoggingInterceptor.Logger {
        override fun log(message: String?) {
            Timber.d(message)
        }
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            // Interceptor to add common headers and auth
            addInterceptor(AuthInterceptor(userAppSession))

            addNetworkInterceptor { chain ->
                // validate network connection
                if (!connectivityDataSource.connected) {
                    throw UnknownHostException()
                }
                return@addNetworkInterceptor chain.proceed(chain.request())
            }
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(HttpLoggingInterceptor(NetworkLogger()).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

        }.connectTimeout(90, TimeUnit.SECONDS).callTimeout(90, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS).build()
    }
    private inner class AuthInterceptor(private val userAppSession: UserAppSession) : Interceptor {
        override fun intercept(chain: Interceptor.Chain):
                Response {
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()

            // Add the "accept" header here as it's a common application-level header
            requestBuilder.addHeader("accept", "application/json")

            // Add Client ID
            requestBuilder.addHeader("clientid", userAppSession.getClient()?.clientId ?: "")

            // Add Authorization header if token exists
            requestBuilder.addHeader("Authorization", "Bearer ${userAppSession.getClient()?.accessToken ?: ""}")

            val newRequest = requestBuilder.build()
            return chain.proceed(newRequest)
        }
    }
    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(createHttpClient())
            .build()
    }

    operator fun <T> get(apiServiceClazz: Class<T>): T {
        return createRetrofit().create(apiServiceClazz)
    }
}
