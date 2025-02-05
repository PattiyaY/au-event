package com.example.auevent.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.auevent.R

@Composable
fun ViewEventPage(navController: NavController, eventTitle: String, eventDetails: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = eventTitle, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Placeholder for Image
        Image(
            painter = painterResource(id = R.drawable.placeholder_image),
            contentDescription = "Event Image",
            modifier = Modifier.height(200.dp).fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        BasicText(text = eventDetails)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Handle Join Event */ }) {
            Text(text = "Join")
        }
    }
}