package com.stwcoding.networkmodule.ktothelper.model.request

abstract class Request(val httpMethod: HttpMethod) {
    open fun getParameters(): Map<String, String>? = null
    open fun getPathSegments(): List<String>? = null
}
