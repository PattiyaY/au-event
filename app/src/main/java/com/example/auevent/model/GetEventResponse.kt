package com.example.auevent.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetEventResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<Event>
)