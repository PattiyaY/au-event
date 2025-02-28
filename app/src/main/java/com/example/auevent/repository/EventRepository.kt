package com.example.auevent.repository

import com.example.auevent.model.CreateEventResponse
import com.example.auevent.model.Event
import com.example.auevent.model.GetEventResponse
import com.example.auevent.model.PostEvent
import com.example.auevent.model.PutEventResponse
import com.example.auevent.network.ApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class EventRepository(private val apiService: ApiService) {
    suspend fun getEventsByCategory(categoryName: String): GetEventResponse {
        return apiService.getEventsByCategory(categoryName)
    }
    suspend fun getAllEvents(): GetEventResponse {
        return apiService.getAllEvents()
    }

    suspend fun getTodaysEvents(): GetEventResponse {
        return apiService.getTodaysEvents()
    }

    suspend fun createEvent(event: PostEvent): CreateEventResponse {
        return apiService.createEvent(event)
    }

    suspend fun getUpcomingEvents(): GetEventResponse {
        return apiService.getUpcomingEvents()
    }

    suspend fun getHostedEvents(): GetEventResponse {
        return apiService.getHostedEvents()
    }

    suspend fun deleteByID(eventId: String): PutEventResponse {
        return apiService.deleteByID(eventId)
    }

    suspend fun updateByID(event: Event): PutEventResponse {
        return apiService.updateByID(event)
    }
}