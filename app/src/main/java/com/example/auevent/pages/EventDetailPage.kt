package com.example.auevent.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.auevent.model.Event
import com.example.auevent.viewmodel.HomeViewModel

@Composable
fun EventDetailPage(
    navController: NavController,
    eventId: String,
    homeViewModel: HomeViewModel
) {
    // Fetch event details (assuming HomeViewModel can get event by ID)
    println("EventDetailPage eventId: $eventId")
    val events by homeViewModel.events.collectAsState()
    println("EventDetailPage events: $events")
    val event = events.find { it._id == eventId } // Find event by ID
    var name by remember { mutableStateOf(event?.name ?: "") } // Editable name
    val error by homeViewModel.error.collectAsState()

    if (event == null) {
        Text("Event not found", modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Event Image
        AsyncImage(
            model = event.imageURL,
            contentDescription = event.name,
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 8.dp)
        )

        // Editable Name Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Event Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Update Button
        Button(
            onClick = {
                val updatedEvent = event.copy(name = name)
                println(updatedEvent)
                homeViewModel.updateEvent(eventId, updatedEvent) // Implement this in ViewModel
                navController.popBackStack() // Go back after update
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Event")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Delete Button
        Button(
            onClick = {
                homeViewModel.deleteEvent(event._id) // Implement this in ViewModel
                navController.popBackStack() // Go back after delete
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete Event")
        }

        // Error Display
        error?.let { errorMessage ->
            Text(
                text = "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}