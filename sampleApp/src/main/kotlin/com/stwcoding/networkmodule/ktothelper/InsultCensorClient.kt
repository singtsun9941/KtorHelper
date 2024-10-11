package com.stwcoding.networkmodule.ktothelper

class InsultCensorClient {
    suspend fun censorWords(uncensored: String): Result<CensorWordsResponse> {
        return InsultCensorAPI.censorWordsAPI.sendRequest(
            request = CensorWordsRequest(
                text = uncensored
            )
        )
    }
}