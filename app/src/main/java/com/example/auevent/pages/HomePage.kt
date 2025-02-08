package com.example.auevent.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.example.auevent.R
import com.example.auevent.model.Category
import kotlinx.coroutines.launch

// Event Data Model
data class Event(val title: String, val imageUrl: String)

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onNavigate = { route ->
                    if (route == "settings") {
                        navController.navigate(route)
                    }
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { scope.launch { drawerState.open() } }
                )
                Text(text = "Home", fontSize = 24.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Jan Poonthong", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Categories Section
            Text(text = "Category", fontSize = 20.sp)
            LazyRow {
                items(categoryList) { category ->
                    CategoryItem(category)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Events Sections
            Text(text = "Today's Events", fontSize = 20.sp)
            EventList(todayEvents)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "All Events", fontSize = 20.sp)
            EventGrid(allEvents)
        }
    }
}

@Composable
fun DrawerContent(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // User Info
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.profile_pic),
                contentDescription = "Profile Picture",
                modifier = Modifier.size(50.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Jan Poonthong", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Navigation Items
        DrawerItem("Settings", Icons.Default.Settings) { onNavigate("settings") }

        Spacer(modifier = Modifier.weight(1f))

        // Sign Out
        DrawerItem("Sign Out", Icons.Default.Logout) { /* Handle Sign Out */ }
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, fontSize = 18.sp)
    }
    Divider()
}

@Composable
fun CategoryItem(name: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .clickable { /* Add click event */ }
            .background(Color(0xFFFFF1E5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(50.dp)) {
            AsyncImage(
                model = "https://raw.githubusercontent.com/JanPoonthong/au-event-api/refs/heads/master/public/social-activities.png",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(text = name, fontSize = 12.sp)
    }
}

@Composable
fun EventList(events: List<Event>) {
    LazyRow {
        items(events) { event ->
            EventItem(event)
        }
    }
}

@Composable
fun EventGrid(events: List<Event>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(events.size) { index ->
            EventItem(events[index])
        }
    }
}

@Composable
fun EventItem(event: Event) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = event.imageUrl,
            contentDescription = event.title,
            modifier = Modifier.size(150.dp)
        )
        Text(text = event.title, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

// Sample Data
val categoryList = listOf(
    Category("Social Activities", "https://example.com/social.jpg"),
    Category("Travel and Outdoor", "https://example.com/travel.jpg"),
    Category("Health and Wellbeing", "https://example.com/health.jpg"),
    Category("Hobbies and Passions", "https://example.com/hobbies.jpg")
)


val todayEvents = listOf(
    Event("AU Christmas Celebration", "https://your-image-url.com/event1.jpg"),
    Event("AU Games 2024", "https://your-image-url.com/event2.jpg")
)

val allEvents = listOf(
    Event("AU Freshly Night", "https://your-image-url.com/event3.jpg"),
    Event("AU Charming Loy Krathong", "https://your-image-url.com/event4.jpg"),
    Event("SLC Camp", "https://your-image-url.com/event5.jpg"),
    Event("AU Festival 2024", "https://your-image-url.com/event6.jpg")
)
