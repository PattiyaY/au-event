package com.example.auevent.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.auevent.R

@Composable
fun EventPage(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Events", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Image(
                painter = painterResource(id = R.drawable.profile_pic),
                contentDescription = "Profile Picture",
                modifier = Modifier.size(40.dp).clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Tabs (Hosted Events / Upcoming Events)
        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text("Hosted Events", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text("Upcoming Events", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Event Grid
        val events = if (selectedTab == 0) hostedEvents else upcomingEvents
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
            items(events) { event ->
                EventItem(event)
            }
        }
    }
}

@Composable
fun EventItem(event: EventData) {
    Column(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = rememberImagePainter(event.imageUrl),
            contentDescription = event.title,
            modifier = Modifier.fillMaxWidth().height(120.dp)
        )
        Text(event.title, fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 8.dp))
    }
}

// Sample Data
val hostedEvents = listOf(
    EventData("AU Christmas Celebration", "https://your-image-url.com/event1.jpg"),
    EventData("AU Games 2024", "https://your-image-url.com/event2.jpg"),
    EventData("AU Fun Run", "https://your-image-url.com/event3.jpg"),
)

val upcomingEvents = listOf(
    EventData("AU Freshly Night", "https://your-image-url.com/event4.jpg"),
    EventData("AU Festival 2024", "https://your-image-url.com/event5.jpg"),
    EventData("SLC Camp", "https://your-image-url.com/event6.jpg"),
)

data class EventData(val title: String, val imageUrl: String)
