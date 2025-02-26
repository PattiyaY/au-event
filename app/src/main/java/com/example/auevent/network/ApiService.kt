package com.example.auevent.network

import com.example.auevent.model.Event
import com.example.auevent.model.GetEventResponse
import com.example.auevent.model.PostEvent
import com.example.auevent.model.PutEventResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
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

    suspend fun getEventsByCategory(categoryName: String): GetEventResponse {
        return try {
            client.use {
                val response = it.get("$baseUrl/category?categoryName=$categoryName").body<GetEventResponse>()
                response
            }
        } catch (e: Exception) {
            println("Error fetching events: ${e.localizedMessage}")
            e.printStackTrace()  // Prints full error trace for debugging
            throw e
        }
    }

    suspend fun getAllEvents(): GetEventResponse {
        return try {
            client.use {
                val response = it.get("$baseUrl/all-events").body<GetEventResponse>()
                response
            }
        } catch (e: Exception) {
            println("Error fetching events: ${e.localizedMessage}")
            e.printStackTrace()  // Prints full error trace for debugging
            throw e
        }
    }

    suspend fun getTodaysEvents(): GetEventResponse {
        return try {
            client.use {
                val response = it.get("$baseUrl/todays-event").body<GetEventResponse>()
                response
            }
        } catch (e: Exception) {
            println("Error fetching events: ${e.localizedMessage}")
            e.printStackTrace()  // Prints full error trace for debugging
            throw e
        }
    }

    suspend fun createEvent(event: PostEvent): GetEventResponse {
        return try {
            val response: GetEventResponse = client.post("$baseUrl/create-event") {
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

    suspend fun getUpcomingEvents(): GetEventResponse {
        return try {
            val response: GetEventResponse = client.get("$baseUrl/upcoming-event").body<GetEventResponse>()
            response
        } catch (e: Exception) {
            println("Error fetching upcoming events: ${e.localizedMessage}")
            e.printStackTrace()
            throw e
        }
    }

    suspend fun getHostedEvents(): GetEventResponse {
        return try {
            val response: GetEventResponse = client.get("$baseUrl/hosted-event").body<GetEventResponse>()
            response
        } catch (e: Exception) {
            println("Error fetching hosted events: ${e.localizedMessage}")
            e.printStackTrace()
            throw e
        }
    }

    @Serializable
    data class DeleteEventRequest(val eventId: String)

    suspend fun deleteByID(eventId: String): PutEventResponse {
        return try {
            val response: PutEventResponse = client.post("$baseUrl/delete-event") {
                contentType(ContentType.Application.Json)
                setBody(DeleteEventRequest(eventId))
            }.body()
            response
        } catch (e: Exception) {
            println("Error delete events with ID: ${e.localizedMessage}")
            e.printStackTrace()
            throw e
        }
    }

    suspend fun updateByID(event: Event): PutEventResponse {
        return try {
            val response: PutEventResponse = client.put("$baseUrl/update-event") {
                contentType(ContentType.Application.Json)
                setBody(event)
            }.body()
            response
        } catch (e: Exception) {
            println("Error update event with ID: ${e.localizedMessage}")
            e.printStackTrace()
            throw e
        }
    }
}