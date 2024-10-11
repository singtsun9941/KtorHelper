package com.stwcoding.networkmodule.ktothelper.model.request

enum class HttpMethod {
    GET,
    POST,
}

fun HttpMethod.toKtorHttpMethod() =
    when (this) {
        HttpMethod.GET -> io.ktor.http.HttpMethod.Get
        HttpMethod.POST -> io.ktor.http.HttpMethod.Post
    }