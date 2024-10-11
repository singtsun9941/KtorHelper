package com.stwcoding.networkmodule.ktothelper.extensions

import com.stwcoding.networkmodule.ktothelper.model.request.Request
import com.stwcoding.networkmodule.ktothelper.model.request.toKtorHttpMethod
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter

fun HttpRequestBuilder.handleRequest(request: Request) {
    method = request.httpMethod.toKtorHttpMethod()
    parameters(request.getParameters())
}

fun HttpRequestBuilder.parameters(parameters: Map<String, String>?) {
    parameters ?: return
    parameters.forEach { (key, value) ->
        parameter(key, value)
    }
}