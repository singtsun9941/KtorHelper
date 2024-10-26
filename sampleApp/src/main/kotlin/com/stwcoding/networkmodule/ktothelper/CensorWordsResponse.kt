package com.stwcoding.networkmodule.ktothelper

import com.stwcoding.networkmodule.ktothelper.model.Response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CensorWordsResponse(
    @SerialName("result")
    val result: String = ""
) : Response