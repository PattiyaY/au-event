package com.example.auevent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auevent.model.Event
import com.example.auevent.network.ApiService
import com.example.auevent.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventViewModel: ViewModel() {
    private val repository = EventRepository(apiService = ApiService())

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getUpcomingEvent() {
        viewModelScope.launch {
            try {
                val result = repository.getUpcomingEvents()
                if (result.success) {
                    _events.value = result.data
                } else {
                    _error.value = "Unknown error"
                }

            } catch (e: Exception) {
                _error.value = "Error fetching upcoming events: ${e.message}"
            }
        }
    }

    fun getHostedEvents() {
        viewModelScope.launch {
            try {
                val result = repository.getHostedEvents()
                if (result.success) {
                    _events.value = result.data
                } else {
                    _error.value = "Unknown error"
                }

            } catch (e: Exception) {
                _error.value = "Error fetching upcoming events: ${e.message}"
            }
        }
    }
}