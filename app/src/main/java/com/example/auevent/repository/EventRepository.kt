package com.example.auevent.repository

import com.example.auevent.model.Event
import com.example.auevent.model.EventResponse
import com.example.auevent.model.PostEvent
import com.example.auevent.network.ApiService

class EventRepository(private val apiService: ApiService) {
    suspend fun getAllEvents(): EventResponse {
        return apiService.getAllEvents()
    }

    suspend fun getTodaysEvents(): EventResponse {
        return apiService.getTodaysEvents()
    }

    suspend fun createEvent(event: PostEvent): EventResponse {
        return apiService.createEvent(event)
    }

    suspend fun getUpcomingEvents(): EventResponse {
        return apiService.getUpcomingEvents()
    }

    suspend fun getHostedEvents(): EventResponse {
        return apiService.getHostedEvents()
    }
}