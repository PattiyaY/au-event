package com.example.auevent.network

import com.example.auevent.model.CreateEventResponse
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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class ApiService {
    private val baseUrl = "http://192.168.1.49:3000/api"

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
        val encodedCategory = URLEncoder.encode(categoryName, StandardCharsets.UTF_8.toString())
        return try {
            val response = client.get("$baseUrl/category?categoryName=$encodedCategory").body<GetEventResponse>()
            response
        } catch (e: Exception) {
            println("Error fetching events: ${e.localizedMessage}")
            e.printStackTrace()  // Prints full error trace for debugging
            throw e
        }
    }

    suspend fun getAllEvents(): GetEventResponse {
        return try {
            val response = client.get("$baseUrl/all-events").body<GetEventResponse>()
            response
        } catch (e: Exception) {
            println("Error fetching events: ${e.localizedMessage}")
            e.printStackTrace()  // Prints full error trace for debugging
            throw e
        }
    }

    suspend fun getTodaysEvents(): GetEventResponse {
        return try {
            val response = client.get("$baseUrl/todays-event").body<GetEventResponse>()
            response
        } catch (e: Exception) {
            println("Error fetching events: ${e.localizedMessage}")
            e.printStackTrace()  // Prints full error trace for debugging
            throw e
        }
    }

    suspend fun createEvent(event: PostEvent): CreateEventResponse {
        return try {
            val response: CreateEventResponse = client.post("$baseUrl/create-event") {
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

    @Serializable
    data class UpdateEventRequest(
        @SerialName("eventId") val eventId: String,
        @SerialName("updateData") val event: Event
    )
    suspend fun updateByID(eventId: String, event: Event): PutEventResponse {
        return try {
            val ob = UpdateEventRequest(eventId, event)
            val response: PutEventResponse = client.put("$baseUrl/update-event") {
                contentType(ContentType.Application.Json)
                setBody(ob)
            }.body()
            response
        } catch (e: Exception) {
            println("Error update event with ID: ${e.localizedMessage}")
            e.printStackTrace()
            throw e
        }
    }
}