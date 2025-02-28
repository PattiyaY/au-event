package com.example.auevent.pages

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
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
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var isNotificationsEnabled by rememberSaveable { mutableStateOf(true) }

    if (isLandscape) {
        Row(
            modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileSection(userData)
            Spacer(modifier = Modifier.width(32.dp))
            SettingsOptions(isDarkMode, onDarkModeToggle, isNotificationsEnabled, onSignOut) {
                isNotificationsEnabled = it
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileSection(userData)
            Spacer(modifier = Modifier.height(16.dp))
            SettingsOptions(isDarkMode, onDarkModeToggle, isNotificationsEnabled, onSignOut) {
                isNotificationsEnabled = it
            }
        }
    }
}

@Composable
fun ProfileSection(userData: UserData?) {
    if (userData?.profilePictureUrl != null) {
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
}

@Composable
fun SettingsOptions(
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    isNotificationsEnabled: Boolean,
    onSignOut: () -> Unit,
    onNotificationToggle: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SwitchItem(Icons.Filled.DarkMode, "Night Mode", isDarkMode, onDarkModeToggle)
        SwitchItem(Icons.Filled.Notifications, "Notifications", isNotificationsEnabled, onNotificationToggle)

        Spacer(modifier = Modifier.height(32.dp))

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
