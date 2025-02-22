package com.example.auevent.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("_id") val _id: String,
    @SerialName("name") val name: String,
    @SerialName("description")     val description: String,
    @SerialName("imageURL")     val imageURL: String,
    @SerialName("date")     val date: String,
    @SerialName("categoryId")     val categoryId: String,
    @SerialName("createdAt")     val createdAt: String
)