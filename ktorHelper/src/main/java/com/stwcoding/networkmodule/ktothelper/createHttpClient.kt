package com.stwcoding.networkmodule.ktothelper

import com.stwcoding.networkmodule.ktothelper.model.request.Platform
import com.stwcoding.networkmodule.ktothelper.model.request.toKtorEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

data class HttpClientConfig(
    val platform: Platform,
    val domain: String,
    val isShowLog: Boolean = true,
)

fun HttpClientConfig.createHttpClient() = HttpClient(platform.toKtorEngine()) {
    defaultRequest {
        url(domain)
    }

    if (isShowLog) {
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    install(ContentNegotiation) {
        json(
            json = Json {
                ignoreUnknownKeys = true
            }
        )
    }

    // TODO https://ktor.io/docs/client-bearer-auth.html
//        install(Auth){
//            bearer{
//                loadToken{}
//                refreshTokens{}
//            }
//        }
}