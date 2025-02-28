package com.example.auevent.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.auevent.model.Event
import com.example.auevent.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoryEventsPage(navController: NavController, homeViewModel: HomeViewModel, categoryName: String) {
    val responses by homeViewModel.response.collectAsState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(categoryName) {
        scope.launch {
            homeViewModel.getEventsByCategory(categoryName)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "$categoryName Events",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (responses.isEmpty()) {
            Text("No events found for $categoryName", fontSize = 16.sp)
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(responses) { event ->
                    EventItem(event, navController)
                }
            }
        }
    }
}

@Composable
fun EventItem(event: Event, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("eventDetail/${event._id}") }
    ) {
        AsyncImage(
            model = event.imageURL,
            contentDescription = event.name,
            modifier = Modifier.size(150.dp)
        )
        Text(text = event.name, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
    }
}