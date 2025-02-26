package com.example.auevent.viewmodel

import androidx.lifecycle.ViewModel
import com.example.auevent.model.Event
import com.example.auevent.network.ApiService
import com.example.auevent.repository.EventRepository
import androidx.lifecycle.viewModelScope
import com.example.auevent.model.GetEventResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val repository = EventRepository(apiService = ApiService())

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _todaysEvents = MutableStateFlow<List<Event>>(emptyList())
    val todaysEvent: StateFlow<List<Event>> = _todaysEvents

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    suspend fun getEventsByCategory(categoryName: String): GetEventResponse {
        println("categoryName: $categoryName")
        return repository.getEventsByCategory(categoryName)
    }

    fun getAllEvents() {
        viewModelScope.launch {
            try {
                println("Inside viewModelScope")
                val result = repository.getAllEvents()
                if (result.success) {
                    _events.value = result.data
                } else {
                    _error.value = "Unknown error"
                }

            } catch (e: Exception) {
                _error.value = "Error fetching all events: ${e.message}"
            }
        }
    }

    fun getTodaysEvents() {
        viewModelScope.launch {
            try {
                val result = repository.getTodaysEvents()
                if (result.success) {
                    _todaysEvents.value = result.data
                } else {
                    _error.value = "Unknown error"
                }

            } catch (e: Exception) {
                _error.value = "Error fetching today's events: ${e.message}"
            }
        }
    }

    fun deleteEvent(_id: String) {
        viewModelScope.launch {
            try {
                val result = repository.deleteByID(_id)
                println(result)
                if (result.success) {
                    _error.value = result.message
                } else {
                    _error.value = "Unknown error"
                }

            } catch (e: Exception) {
                _error.value = "Error delete event: ${e.message}"
            }
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            try {
                val result = repository.updateByID(event)
                if (result.success) {
                    _error.value = result.message
                } else {
                    _error.value = "Unknown error"
                }

            } catch (e: Exception) {
                _error.value = "Error update event: ${e.message}"
            }
        }
    }
}