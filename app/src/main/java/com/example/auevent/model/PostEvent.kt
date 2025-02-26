package com.example.auevent.model

import kotlinx.serialization.Serializable

@Serializable
data class PostEvent(
    val name: String,
    val description: String,
    val time: String,
    val imageURL: String,
    val date: String,
    val category: String
)
