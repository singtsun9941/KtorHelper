package com.stwcoding.networkmodule.ktothelper.model

import com.stwcoding.networkmodule.ktothelper.model.request.Request

abstract class API<REQUEST : Request, RESPONSE : Response>(
    protected val path: String,
) {
    override fun toString(): String {
        return "API Path: $path"
    }

    abstract suspend fun sendRequest(request: REQUEST): Result<RESPONSE>
}