package com.stwcoding.networkmodule.ktothelper.model

sealed class NetworkError(errorMsg: String) : Error("Network Error: $errorMsg") {
    class RequestTimeout : NetworkError("Request timeout")
    class Conflict : NetworkError("Conflict")
    class NoInternet : NetworkError("No internet")
    class PayloadTooLarge : NetworkError("Payload too large")
    class Unauthorized : NetworkError("Unauthorized")
    data class ServerError(val stateCode: Int) : NetworkError("Server error")
    class Serialization : NetworkError("Serialization")
    data class Unknown(val stateCode: Int) : NetworkError("Unknown")
}