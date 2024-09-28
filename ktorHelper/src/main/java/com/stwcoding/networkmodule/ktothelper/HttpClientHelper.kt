package com.stwcoding.networkmodule.ktothelper

import com.stwcoding.networkmodule.ktothelper.model.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

abstract class HttpClientHelper(
    protected val httpClient: HttpClient,
    protected val getRequestBuilder: HttpRequestBuilder.() -> Unit = {},
    protected val postRequestBuilder: HttpRequestBuilder.() -> Unit = {}
) {
    protected suspend inline fun <reified T> get(
        path: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        request(
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
        request(
            httpMethod = HttpMethod.Post,
            path = path,
        ) {
            postRequestBuilder()
            block()
        }

    protected suspend inline fun <reified T> request(
        httpMethod: HttpMethod,
        path: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        handleRequest {
            httpClient.request(path) {
                method = httpMethod
                block()
            }
        }

    protected suspend inline fun <reified T> handleRequest(block: () -> HttpResponse) = try {
        block().getStatusCodeResult<T>()
    } catch (e: UnresolvedAddressException) {
        Result.failure(NetworkError.NoInternet())
    } catch (e: SerializationException) {
        Result.failure(NetworkError.Serialization())
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    protected suspend inline fun <reified T> HttpResponse.getStatusCodeResult() =
        when (val statusCode = status.value) {
            in 200..299 -> Result.success(body<T>())
            401 -> Result.failure(NetworkError.Unauthorized())
            409 -> Result.failure(NetworkError.Conflict())
            408 -> Result.failure(NetworkError.RequestTimeout())
            413 -> Result.failure(NetworkError.PayloadTooLarge())
            in 500..599 -> handleServerError(this)
            else -> Result.failure(NetworkError.Unknown(statusCode))
        }

    protected fun <T> handleServerError(response: HttpResponse): Result<T> {
        return Result.failure(NetworkError.ServerError(response.status.value))
    }
}