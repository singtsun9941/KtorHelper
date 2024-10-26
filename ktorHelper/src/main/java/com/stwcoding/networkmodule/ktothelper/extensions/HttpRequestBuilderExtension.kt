package com.stwcoding.networkmodule.ktothelper.extensions

import com.stwcoding.networkmodule.ktothelper.model.request.Request
import com.stwcoding.networkmodule.ktothelper.model.request.toKtorHttpMethod
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments

fun HttpRequestBuilder.handleRequest(request: Request) {
    request.run {
        method = httpMethod.toKtorHttpMethod()
        pathSegments(getPathSegments())
        parameters(getParameters())
    }
}

fun HttpRequestBuilder.parameters(parameters: Map<String, String>?) {
    parameters ?: return
    parameters.forEach { (key, value) ->
        parameter(key, value)
    }
}

fun HttpRequestBuilder.pathSegments(pathSegments: List<String>?) {
    pathSegments ?: return
    url {
        appendPathSegments(pathSegments)
    }
}