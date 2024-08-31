package com.stwcoding.ktothelper

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CensorWordsResponse(
    @SerialName("result")
    val result: String = ""
)