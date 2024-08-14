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
    private val httpClient: HttpClient,
) {
    open suspend fun get(
        path: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<String> =
        requestHelper(
            httpMethod = HttpMethod.Get,
            path = path,
            block = block,
        )

    open suspend fun post(
        path: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<String> =
        requestHelper(
            httpMethod = HttpMethod.Post,
            path = path,
            block = block,
        )

    private suspend fun requestHelper(
        httpMethod: HttpMethod,
        path: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<String> {
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

        return when (response.status.value) {
            in 200..299 -> {
                val censoredText = response.body<String>()
                Result.success(censoredText)
            }

            401 -> Result.failure(NetworkError.UNAUTHORIZED())
            409 -> Result.failure(NetworkError.CONFLICT())
            408 -> Result.failure(NetworkError.REQUEST_TIMEOUT())
            413 -> Result.failure(NetworkError.PAYLOAD_TOO_LARGE())
            in 500..599 -> Result.failure(NetworkError.SERVER_ERROR())
            else -> Result.failure(NetworkError.UNKNOWN())
        }
    }
}