package com.stwcoding.networkmodule.ktothelper

import com.stwcoding.networkmodule.ktothelper.model.API
import com.stwcoding.networkmodule.ktothelper.model.request.Platform

object InsultCensorAPI : KtorAPI(
    HttpClientConfig(
        platform = Platform.ANDROID,
        domain = "https://www.purgomalum.com"
    )
) {
    val censorWordsAPI: API<CensorWordsRequest, CensorWordsResponse> =
        object : API<CensorWordsRequest, CensorWordsResponse>(path = "service/json") {
            override suspend fun sendRequest(request: CensorWordsRequest): Result<CensorWordsResponse> {
                return request(path = path, request = request)
            }
        }
}
