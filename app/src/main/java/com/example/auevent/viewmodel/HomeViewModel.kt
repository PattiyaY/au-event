package com.example.auevent.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auevent.model.Event
import com.example.auevent.model.GetEventResponse
import com.example.auevent.model.PostEvent
import com.example.auevent.model.PutEventResponse
import com.example.auevent.network.ApiService
import com.example.auevent.utils.StorageUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val apiService = ApiService() // Directly using API service
    private val storageUtil = StorageUtil()

    private val _response = MutableStateFlow<List<Event>>(emptyList())
    val response: StateFlow<List<Event>> = _response

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _todaysEvents = MutableStateFlow<List<Event>>(emptyList())
    val todaysEvents: StateFlow<List<Event>> = _todaysEvents

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _createResponse = MutableStateFlow<Event?>(null)
    val createResponse: StateFlow<Event?> = _createResponse


    suspend fun getEventsByCategory(categoryName: String): GetEventResponse {
        return apiService.getEventsByCategory(categoryName)
    }

    fun getAllEvents() {
        viewModelScope.launch {
            try {
                val result = apiService.getAllEvents()
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
                val result = apiService.getTodaysEvents()
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

    fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            try {
                val result: PutEventResponse = apiService.deleteByID(eventId)
                if (result.success) {
                    _error.value = result.message
                } else {
                    _error.value = "Unknown error"
                }
            } catch (e: Exception) {
                _error.value = "Error deleting event: ${e.message}"
            }
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            try {
                val result: PutEventResponse = apiService.updateByID(event)
                if (result.success) {
                    _error.value = result.message
                } else {
                    _error.value = "Unknown error"
                }
            } catch (e: Exception) {
                _error.value = "Error updating event: ${e.message}"
            }
        }
    }

    fun createEvent(event: PostEvent, imageURL: Uri, context: Context, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val uploadedImageUrl = storageUtil.uploadToStorage(imageURL, context, "image")

                if (uploadedImageUrl != null) {
                    val newEvent = event.copy(imageURL = uploadedImageUrl)
                    val result = apiService.createEvent(newEvent)

                    if (result.success) {
                        _createResponse.value = result.data
                        onComplete(true)
                    } else {
                        _error.value = "Unknown error"
                        onComplete(false)
                    }
                } else {
                    _error.value = "Failed to upload image"
                    onComplete(false)
                }
            } catch (e: Exception) {
                _error.value = "Error creating event: ${e.message}"
                onComplete(false)
            }
        }
    }
}
