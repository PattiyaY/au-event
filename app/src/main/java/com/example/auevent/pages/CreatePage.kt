package com.example.auevent.pages

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.auevent.model.PostEvent
import com.example.auevent.sendNotification
import com.example.auevent.utils.StorageUtil
import com.example.auevent.viewmodel.HomeViewModel
import java.io.File
import java.util.*


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreatePage(modifier: Modifier = Modifier, homeViewModel: HomeViewModel, navController: NavController) {
    val response by homeViewModel.response.collectAsState()

    var description by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val selectedDate = remember { mutableStateOf("") }
    val selectedTime = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) } // Error state

    val context = LocalContext.current
    val storageUtil = remember { StorageUtil() }

    val isFormComplete = title.isNotBlank() && description.isNotBlank() &&
            selectedCategory != null && selectedDate.value.isNotBlank() &&
            selectedTime.value.isNotBlank() && selectedImageUri != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Enter event name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = title, onValueChange = { title = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Type the event name...") })

        Text(text = "Create a new event", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = description, onValueChange = { description = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Type the description...") })

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(value = selectedDate.value, onValueChange = {}, label = { Text("Select Date") }, enabled = false, modifier = Modifier
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
                })

            TextField(value = selectedTime.value, onValueChange = {}, label = { Text("Select Time") }, enabled = false, modifier = Modifier
                .weight(1f)
                .clickable {
                    val calendar = Calendar.getInstance()
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute -> selectedTime.value = "$hourOfDay:$minute" },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                })
        }

        Text(text = "Attach an image", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Box(modifier = Modifier
            .size(60.dp)
            .background(Color.LightGray, shape = CircleShape)
            .clickable { showDialog.value = true }, contentAlignment = Alignment.Center) {
            Text(text = "+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        if (showDialog.value) {
            ImagePickerDialog(showDialog, onImageSelected = { uri -> selectedImageUri = uri })
        }

        Text(text = "Choose the category", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        FlowRow(modifier = Modifier.fillMaxWidth()) {
            listOf("Social Activities", "Travel and Outdoor", "Health and Wellbeing", "Hobbies and Passions").forEach { category ->
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

        Spacer(modifier = Modifier.height(16.dp))

        if (showError) {
            Text(
                text = "⚠️ Fill up all fields",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                if (isFormComplete) {
                    val event = PostEvent(
                        name = title,
                        description = description,
                        imageURL = "",
                        date = selectedDate.value,
                        category = selectedCategory.toString(),
                        time = selectedTime.value,
                    )

                    homeViewModel.createEvent(event, selectedImageUri!!, context) { success ->
                        if (success) {
                            println("HERE")
                            sendNotification(context, "Event Published", "Your event '$title' has been successfully published!")

                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true } // Removes create event from back stack
                            }
                        }
                    }
                    showError = false
                } else {
                    showError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (isFormComplete) Color(0xFF6A1B9A) else Color(0xFFBDBDBD)), // Change color
            shape = RoundedCornerShape(50),
            enabled = isFormComplete // Disable button when form is incomplete
        ) {
            Text(text = "Publish", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun ImagePickerDialog(showDialog: MutableState<Boolean>, onImageSelected: (Uri) -> Unit) {
    val context = LocalContext.current
    val storageUtil = remember { StorageUtil() }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    val getTempImageUri = {
        val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir).apply { createNewFile(); deleteOnExit() }
        FileProvider.getUriForFile(context, "${context.packageName}.provider", tempFile).also { tempImageUri = it }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && tempImageUri != null) {
            onImageSelected(tempImageUri!!)
            showDialog.value = false
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            onImageSelected(it)
            showDialog.value = false
        }
    }

    AlertDialog(
        title = { Text(text = "Pick an option") },
        text = {
            Column {
                Button(onClick = { cameraLauncher.launch(getTempImageUri()) }) { Text(text = "Select Camera") }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { galleryLauncher.launch("image/*") }) { Text(text = "Select Gallery") }
            }
        },
        onDismissRequest = { showDialog.value = false },
        confirmButton = {},
        dismissButton = { TextButton(onClick = { showDialog.value = false }) { Text("Dismiss") } }
    )
}
