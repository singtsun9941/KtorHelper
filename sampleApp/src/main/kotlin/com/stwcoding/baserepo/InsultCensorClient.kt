package com.stwcoding.baserepo

import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.parameter

class InsultCensorClient : HttpClientHelper(
    createHttpClient(
        engine = OkHttp.create(), // Darwin for ios
        domain = "https://www.purgomalum.com"
    )
) {
    suspend fun censorWords(uncensored: String): Result<CensorWordsResponse> {
        return get("service/json") {
            parameter("text", uncensored)
        }
    }
}