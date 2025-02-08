package com.example.auevent.pages

import androidx.annotation.DrawableRes
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
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Category
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.auevent.R
import kotlinx.coroutines.launch

// Event Data Model
data class Event(val title: String, val imageUrl: String)

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Transparent,
        drawerContent = {
            Box(
                modifier = Modifier
                    .width(screenWidth * 0.5f) // Open half of the screen
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(16.dp)
            )

            DrawerContent(
                onNavigate = { route ->
                    val currentRoute = navController.currentDestination?.route
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                        }
                    }
                    scope.launch { drawerState.close() } // Ensure drawer closes on navigation
                },
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
                    items(categories) { category ->
                        CategoryItem(category)
                    }
                }
            Spacer(modifier = Modifier.height(8.dp))

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
            .fillMaxSize(0.5f)
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
        DrawerItem("Category", Icons.Default.Category) { onNavigate("home") }
        DrawerItem("Events", Icons.Default.AddLocation) { onNavigate("event") }
        DrawerItem("Settings", Icons.Default.Settings) { onNavigate("settings") }
//        Spacer(modifier = Modifier.weight(1f))

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
//    Divider()
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .clickable { /* Add click event */ }
//            .background(Color(0xFFFFF1E5))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.pictureRes),
            contentDescription = "Category Image",
            modifier = Modifier.size(50.dp)
        )
        Text(text = category.name, fontSize = 8.sp)
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

data class Category(val name: String, @DrawableRes val pictureRes: Int)

val categories = listOf(
    Category("Social Activities", R.drawable.ic_social),
    Category("Travel and Outdoor", R.drawable.ic_travel),
    Category("Health and Wellbeing", R.drawable.ic_health),
    Category("Hobbies and Passions", R.drawable.ic_hobbies)
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
