package com.stwcoding.networkmodule.ktothelper.model.request

abstract class Request(val httpMethod: HttpMethod) {
    private val parameters = mutableMapOf<String, String>()
    private val pathSegments = mutableListOf<String>()

    fun getParameters() = parameters.toMap()
    fun getPathSegments() = pathSegments.toList()

    fun addParameters(vararg args: Pair<String, String>) {
        parameters.putAll(args)
    }

    fun addPathSegments(vararg args: String) {
        pathSegments.addAll(args)
    }
}
