package com.stwcoding.networkmodule.ktothelper

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(
    engine: HttpClientEngine,
    domain: String,
    isShowLog: Boolean = true,
    config: HttpClientConfig<*>.() -> Unit = {}
): HttpClient {
    return HttpClient(engine) {
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

        config()
    }
}