package com.example.auevent.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auevent.model.Event
import com.example.auevent.model.PostEvent
import com.example.auevent.network.ApiService
import com.example.auevent.repository.EventRepository
import com.example.auevent.utils.StorageUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateViewModel(private val homeViewModel: HomeViewModel): ViewModel() {
    private val repository = EventRepository(apiService = ApiService())
    val storageUtil = StorageUtil()

    private val _response = MutableStateFlow<List<Event>>(emptyList())
    val response: StateFlow<List<Event>> = _response

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun createEvent(event: PostEvent, imageURL: Uri, context: Context, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val imageUrl = storageUtil.uploadToStorage(imageURL, context, "image")

                if (imageUrl != null) {
                    val newEvent = event.copy(imageURL = imageUrl)
                    val result = repository.createEvent(newEvent)

                    if (result.success) {
                        _response.value = result.data
                        onComplete(result.success)
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