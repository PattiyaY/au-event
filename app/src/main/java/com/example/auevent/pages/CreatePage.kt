package com.example.auevent.pages

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreatePage(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    var description by remember { mutableStateOf("") }
    val showDialog = remember{mutableStateOf(false)}

    val categories = listOf(
        "Social Activities",
        "Travel and Outdoor",
        "Health and Wellbeing",
        "Hobbies and Passions"
    )
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

        val context = LocalContext.current

        // States to store selected date and time
        val selectedDate = remember { mutableStateOf("") }
        val selectedTime = remember { mutableStateOf("") }

        Row(modifier = Modifier.fillMaxWidth()) {
            // Date Picker
            TextField(
                value = selectedDate.value,
                onValueChange = {},
                label = { Text("Select Date") },
                enabled = false, // Disable manual input
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                selectedDate.value = "$dayOfMonth/${month + 1}/$year"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
            )

            // Time Picker
            TextField(
                value = selectedTime.value,
                onValueChange = {},
                label = { Text("Select Time") },
                enabled = false, // Disable manual input
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        val calendar = Calendar.getInstance()
                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                selectedTime.value = "$hourOfDay:$minute"
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    }
            )
        }

        Text(
            text = "Attach an image or a video",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray, shape = CircleShape)
                    .clickable {
                        showDialog.value = true;
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                if (showDialog.value) {
                    alert(showDialog)
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
                        .background(
                            if (isSelected) Color(0xFFE91E63) else Color.LightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
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

@Composable
fun alert(showDialog: MutableState<Boolean>) {
    AlertDialog(
        title = {
            Text(text = "Pick an option")
        },
        text = {
            Column {
                Button(onClick = {}) {
                    Text(text = "Select Camera")
                }
                Button(onClick = {}) {
                    Text(text = "Select Gallery")
                }
                Button(onClick = {}) {
                    Text(text = "Select File")
                }
            }
        },
        onDismissRequest = {
        },
        confirmButton = {
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}