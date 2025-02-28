package com.example.auevent.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.auevent.R
import com.example.auevent.model.UserData

@Composable
fun SettingPage(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    userData: UserData?,
    onSignOut: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf("English") }
    var showLanguageMenu by remember { mutableStateOf(false) }
    var isNotificationsEnabled by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData?.profilePictureUrl != null) {
            // Profile Picture
            Box(contentAlignment = Alignment.BottomEnd) {
                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile Picture",
                    placeholder = painterResource(R.drawable.profile_pic),
                    error = painterResource(R.drawable.profile_pic),
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Name Field
        if(userData?.userName != null) {
            SettingItem(Icons.Filled.Person, "Name", userData.userName)
        }

        // Notifications Toggle
        SwitchItem(Icons.Filled.Notifications, "Notifications", isNotificationsEnabled) {
            isNotificationsEnabled = it
        }

        // Night Mode Toggle
        SwitchItem(Icons.Filled.DarkMode, "Night Mode", isDarkMode, onDarkModeToggle)

        // Language Selector
        // Box(
        //     modifier = Modifier.fillMaxWidth().clickable { showLanguageMenu = true }.padding(12.dp)
        // ) {
        //     Row(
        //         modifier = Modifier.fillMaxWidth(),
        //         verticalAlignment = Alignment.CenterVertically
        //     ) {
        //         Icon(Icons.Filled.Translate, contentDescription = "Language", tint = Color.Gray, modifier = Modifier.size(24.dp))
        //         Spacer(modifier = Modifier.width(12.dp))
        //         Text("Language", fontSize = 16.sp)
        //         Spacer(modifier = Modifier.weight(1f))
        //         Text(selectedLanguage, fontSize = 16.sp, color = Color.Gray)
        //         Spacer(modifier = Modifier.width(8.dp))
        //         Icon(Icons.Filled.ArrowForwardIos, contentDescription = "Select Language", tint = Color.Gray, modifier = Modifier.size(16.dp))
        //     }
        // }

        // Language Dropdown Menu
        // DropdownMenu(
        //     expanded = showLanguageMenu,
        //     onDismissRequest = { showLanguageMenu = false }
        // ) {
        //     DropdownMenuItem(
        //         text = { Text("English") },
        //         onClick = {
        //             selectedLanguage = "English"
        //             showLanguageMenu = false
        //         }
        //     )
        //     DropdownMenuItem(
        //         text = { Text("Thai") },
        //         onClick = {
        //             selectedLanguage = "Thai"
        //             showLanguageMenu = false
        //         }
        //     )
        // }

        Spacer(modifier = Modifier.height(32.dp))

        // Sign Out Button
        Button(
            onClick = onSignOut,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Sign Out", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun SwitchItem(icon: ImageVector, title: String, checkedState: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = checkedState, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingItem(icon: ImageVector, title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(value, fontSize = 16.sp, color = Color.Gray)
    }
}