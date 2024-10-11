package com.stwcoding.networkmodule.ktothelper

import com.stwcoding.networkmodule.ktothelper.model.request.HttpMethod
import com.stwcoding.networkmodule.ktothelper.model.request.Request

data class CensorWordsRequest(
    val text: String,
) : Request(HttpMethod.GET) {
    override fun getParameters(): Map<String, String> {
        return mapOf(
            "text" to text
        )
    }
}
