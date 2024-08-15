package com.stwcoding.baserepo

import com.stwcoding.baserepo.model.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException

abstract class HttpClientHelper(
    protected val httpClient: HttpClient,
    protected val getRequestBuilder: HttpRequestBuilder.() -> Unit = {},
    protected val postRequestBuilder: HttpRequestBuilder.() -> Unit = {}
) {
    protected suspend inline fun <reified T> get(
        path: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        requestHelper(
            httpMethod = HttpMethod.Get,
            path = path,
        ) {
            getRequestBuilder()
            block()
        }

    protected suspend inline fun <reified T> post(
        path: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        requestHelper(
            httpMethod = HttpMethod.Post,
            path = path,
        ) {
            postRequestBuilder()
            block()
        }

    protected suspend inline fun <reified T> requestHelper(
        httpMethod: HttpMethod,
        path: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> {
        val response = try {
            httpClient.request(path) {
                method = httpMethod
                block()
            }
        } catch (e: UnresolvedAddressException) {
            return Result.failure(NetworkError.NO_INTERNET())
        } catch (e: SerializationException) {
            return Result.failure(NetworkError.SERIALIZATION())
        }

        return when (val statusCode = response.status.value) {
            in 200..299 -> {
                val responseBody = response.body<T>()
                Result.success(responseBody)
            }

            401 -> Result.failure(NetworkError.UNAUTHORIZED())
            409 -> Result.failure(NetworkError.CONFLICT())
            408 -> Result.failure(NetworkError.REQUEST_TIMEOUT())
            413 -> Result.failure(NetworkError.PAYLOAD_TOO_LARGE())
            in 500..599 -> Result.failure(NetworkError.SERVER_ERROR(statusCode))
            else -> Result.failure(NetworkError.UNKNOWN(statusCode))
        }
    }
}