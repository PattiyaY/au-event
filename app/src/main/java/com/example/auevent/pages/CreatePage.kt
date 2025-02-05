package com.example.auevent.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreatePage(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    var description by remember { mutableStateOf("") }
    val categories = listOf("Festivals", "Sports", "Camp", "Social Activities", "Travel and Outdoor", "Health and Wellbeing", "Hobbies and Passions")
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🔙 Back Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackClick() }) {  // ✅ Ensures navigation callback is called
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }

        Text(text = "Create a new event", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            placeholder = { Text("Type the description...") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Attach the image", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.LightGray, shape = CircleShape)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Choose the category", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            categories.forEach { category ->
                val isSelected = category == selectedCategory
                Text(
                    text = category,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(if (isSelected) Color(0xFFE91E63) else Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .clickable { selectedCategory = category }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Publish Action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Publish", fontSize = 18.sp, color = Color.White)
        }
    }
}
