package com.stwcoding.baserepo.model

sealed class NetworkError : Error("Network Error") {
    class REQUEST_TIMEOUT : NetworkError()
    class TOO_MANY_REQUEST : NetworkError()
    class CONFLICT : NetworkError()
    class NO_INTERNET : NetworkError()
    class PAYLOAD_TOO_LARGE : NetworkError()
    class UNAUTHORIZED : NetworkError()
    data class SERVER_ERROR(val errorCode: Int) : NetworkError()
    class SERIALIZATION : NetworkError()
    class UNKNOWN : NetworkError()
}