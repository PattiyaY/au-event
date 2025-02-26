package com.example.auevent.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PutEventResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("message") val message: String
)