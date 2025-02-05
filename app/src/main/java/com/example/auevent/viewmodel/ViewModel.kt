package com.example.auevent.viewmodel

import android.app.usage.UsageEvents.Event
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {
    // Shared state for events
    val events: SnapshotStateList<Event> = mutableStateListOf()
}