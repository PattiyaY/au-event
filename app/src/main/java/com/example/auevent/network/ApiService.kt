package com.example.auevent.network

import com.example.auevent.model.EventResponse
import com.example.auevent.model.PostEvent
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiService {
    private val baseUrl = "http://192.168.80.150:3000/api"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true  // Prevents crash due to extra fields
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getAllEvents(): EventResponse {
        return try {
            client.use {
                val response = it.get("$baseUrl/all-events").body<EventResponse>()
                response
            }
        } catch (e: Exception) {
            println("Error fetching events: ${e.localizedMessage}")
            e.printStackTrace()  // Prints full error trace for debugging
            throw e
        }
    }

    suspend fun getTodaysEvents(): EventResponse {
        return try {
            client.use {
                val response = it.get("$baseUrl/todays-event").body<EventResponse>()
                response
            }
        } catch (e: Exception) {
            println("Error fetching events: ${e.localizedMessage}")
            e.printStackTrace()  // Prints full error trace for debugging
            throw e
        }
    }

    suspend fun createEvent(event: PostEvent): EventResponse {
        return try {
            val response: EventResponse = client.post("$baseUrl/create-event") {
                contentType(ContentType.Application.Json)
                setBody(event)
            }.body()
            response
        } catch (e: Exception) {
            println("Error creating event: ${e.localizedMessage}")
            e.printStackTrace()
            throw e
        }
    }

    suspend fun getUpcomingEvents(): EventResponse {
        return try {
            val response: EventResponse = client.get("$baseUrl/upcoming-event").body<EventResponse>()
            response
        } catch (e: Exception) {
            println("Error fetching upcoming events: ${e.localizedMessage}")
            e.printStackTrace()
            throw e
        }
    }

    suspend fun getHostedEvents(): EventResponse {
        return try {
            val response: EventResponse = client.get("$baseUrl/hosted-event").body<EventResponse>()
            response
        } catch (e: Exception) {
            println("Error fetching hosted events: ${e.localizedMessage}")
            e.printStackTrace()
            throw e
        }
    }

    suspend fun deleteByID(): 
}