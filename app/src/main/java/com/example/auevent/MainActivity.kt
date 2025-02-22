package com.example.auevent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.auevent.pages.*
import com.example.auevent.ui.theme.AUEventTheme
import com.example.auevent.viewmodel.CreateViewModel
import com.example.auevent.viewmodel.EventViewModel
import com.example.auevent.viewmodel.HomeViewModel

class CreateViewModelFactory(private val homeViewModel: HomeViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateViewModel(homeViewModel) as T
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            val navController = rememberNavController()

            AUEventTheme(darkTheme = isDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            val homeViewModel: HomeViewModel = viewModel()
                            HomePage(
                            navController = navController,
                            homeViewModel = homeViewModel
                        ) }
                        composable("create") {
                            val homeViewModel: HomeViewModel = viewModel()
                            val createViewModel: CreateViewModel = viewModel(factory = CreateViewModelFactory(homeViewModel))
                            CreatePage(
                            onBackClick = { navController.popBackStack() },
                            createViewModel = createViewModel,
                        ) }
                        composable("event") {
                            val eventViewModel: EventViewModel = viewModel()
                            EventPage(
                                eventViewModel = eventViewModel
                            )
                        }
                        composable("settings") {
                            SettingPage(
                                isDarkMode = isDarkMode,
                                onDarkModeToggle = { isDarkMode = it }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home, "home"),
        BottomNavigationItem("Create", Icons.Filled.Add, Icons.Outlined.Add, "create"),
        BottomNavigationItem("Event", Icons.Filled.DateRange, Icons.Outlined.DateRange, "event"),
        BottomNavigationItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings, "settings")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo("home") { saveState = true }
                        }
                    }
                },
                label = { Text(text = item.title) },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}
