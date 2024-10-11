package com.stwcoding.networkmodule.ktothelper.model.request

import io.ktor.client.engine.okhttp.OkHttp

enum class Platform {
    ANDROID,
    IOS
}

internal fun Platform.toKtorEngine() = when (this) {
    Platform.ANDROID -> OkHttp.create()
    Platform.IOS -> TODO()
}