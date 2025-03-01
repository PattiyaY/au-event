package com.example.auevent.pages

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.auevent.MusicService
import com.example.auevent.R
import com.example.auevent.model.Event
import com.example.auevent.model.UserData
import com.example.auevent.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel,
    userData: UserData?
) {
    val events by homeViewModel.events.collectAsState()
    val error by homeViewModel.error.collectAsState()
    val todaysEvents by homeViewModel.todaysEvents.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    // Scroll state for main content
    val scrollState = rememberScrollState()

    // Use LaunchedEffect for composable-scoped coroutines
    LaunchedEffect(Unit) {
        homeViewModel.getAllEvents()
        homeViewModel.getTodaysEvents()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Transparent,
        drawerContent = {
            Box(
                modifier = Modifier
                    .width(screenWidth * (if (isLandscape) 0.3f else 0.5f)) // Narrower drawer in landscape
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(16.dp)
            )

            DrawerContent(
                userData = userData,
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
                .then(if (!isLandscape) Modifier else Modifier.verticalScroll(scrollState))
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
                    if (userData?.userName != null && userData.profilePictureUrl != null) {
                        Text(text = userData.userName, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        AsyncImage(
                            model = userData.profilePictureUrl,
                            contentDescription = "Profile Picture",
                            placeholder = painterResource(R.drawable.profile_pic),
                            error = painterResource(R.drawable.profile_pic),
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (isLandscape) {
                // Landscape layout - side by side sections
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Today's Events Section - takes 40% in landscape
                    Column(modifier = Modifier.weight(0.4f)) {
                        Text(text = "Today's Events", fontSize = 20.sp)
                        EventListLandscape(todaysEvents, navController)
                    }
                    // All Events Section - takes 60% in landscape
                    Column(modifier = Modifier.weight(0.6f)) {
                        Text(text = "All Events", fontSize = 20.sp)
                        EventGridLandscape(events, navController)
                    }
                }
            } else {
                // Portrait layout - stacked sections
                Text(text = "Today's Events", fontSize = 20.sp)
                EventList(todaysEvents, navController)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "All Events", fontSize = 20.sp)
                EventGrid(events, navController)
            }
        }
    }
}

@Composable
fun DrawerContent(userData: UserData?, onNavigate: (String) -> Unit) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    Column(
        modifier = Modifier
            .fillMaxSize(if (isLandscape) 0.3f else 0.5f)
            .padding(16.dp)
    ) {
        // User Info
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (userData?.userName != null && userData.profilePictureUrl != null) {
                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile Picture",
                    placeholder = painterResource(R.drawable.profile_pic),
                    error = painterResource(R.drawable.profile_pic),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = userData.userName, fontSize = 18.sp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Navigation Items
        DrawerItem("Category", Icons.Default.Category) { onNavigate("category") }
        DrawerItem("Events", Icons.Default.AddLocation) { onNavigate("event") }
        DrawerItem("Settings", Icons.Default.Settings) { onNavigate("settings") }
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
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .clickable { /* Add click event */ }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.pictureRes),
            contentDescription = "Category Image",
            modifier = Modifier.size(50.dp)
        )
        Text(text = category.name, fontSize = 12.sp)
    }
}

// Original EventList for portrait mode
@Composable
fun EventList(events: List<Event>, navController: NavController) {
    LazyRow {
        items(events) { event ->
            HomeEventItem(event, navController)
        }
    }
}

// Landscape mode EventList
@Composable
fun EventListLandscape(events: List<Event>, navController: NavController) {
    LazyRow {
        items(events) { event ->
            HomeEventItem(
                event = event,
                navController = navController,
                modifier = Modifier.width(130.dp) // Slightly smaller for landscape
            )
        }
    }
}

// Original EventGrid for portrait mode
@Composable
fun EventGrid(events: List<Event>, navController: NavController) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(events.size) { index ->
            HomeEventItem(events[index], navController)
        }
    }
}

// Landscape mode EventGrid
@Composable
fun EventGridLandscape(events: List<Event>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // More columns in landscape
        modifier = Modifier.height(250.dp) // Fixed height to fit in landscape
    ) {
        items(events.size) { index ->
            HomeEventItem(
                event = events[index],
                navController = navController,
                modifier = Modifier.width(120.dp) // Slightly smaller for landscape
            )
        }
    }
}

@Composable
fun HomeEventItem(
    event: Event,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .clickable { navController.navigate("eventDetail/${event._id}") },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = event.imageURL,
            contentDescription = event.name,
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = event.name,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

data class Category(val name: String, @DrawableRes val pictureRes: Int)

val categories = listOf(
    Category("Social Activities", R.drawable.ic_social),
    Category("Travel and Outdoor", R.drawable.ic_travel),
    Category("Health and Wellbeing", R.drawable.ic_health),
    Category("Hobbies and Passions", R.drawable.ic_hobbies)
)