package com.stwcoding.networkmodule.ktothelper

import com.stwcoding.networkmodule.ktothelper.model.request.HttpMethod
import com.stwcoding.networkmodule.ktothelper.model.request.Request

data class CensorWordsRequest(
    val text: String,
) : Request(HttpMethod.GET) {
    init {
        addParameters("text" to text)
    }
}
