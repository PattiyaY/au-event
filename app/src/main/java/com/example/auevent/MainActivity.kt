package com.example.auevent

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.auevent.pages.*
import com.example.auevent.ui.theme.AUEventTheme
import com.example.auevent.viewmodel.EventViewModel
import com.example.auevent.viewmodel.HomeViewModel
import com.example.auevent.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.onesignal.OneSignal
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    @SuppressLint("UnrememberedGetBackStackEntry", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
//        OneSignal.initWithContext(this, "e89c57b7-9e20-4742-86d3-cdebc2e07caf")

        super.onCreate(savedInstanceState)

        // Create a proper LifecycleScope for HomeViewModel
        val homeViewModel: HomeViewModel by viewModels()

        // lifecycleScope.launch {
        //     homeViewModel.getAllEvents()
        //     homeViewModel.getTodaysEvents()
        // }

        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val musicServiceIntent = Intent(this, MusicService::class.java)
            startService(musicServiceIntent)

            // Launch data loading in proper lifecycle scope
            // LaunchedEffect(key1 = Unit) {
            //     homeViewModel.getAllEvents()
            //     homeViewModel.getTodaysEvents()
            // }

            AUEventTheme(darkTheme = isDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute != "sign_in") { // Hide bottom bar on Sign In screen
                            BottomNavigationBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if(googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate("home")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(intent = result.data ?: return@launch)
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                    }
                            )
                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if(state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate("home")
                                    viewModel.resetState()
                                }
                            }
                            SignInPage(state = state, onSignInClick = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                    ).build()
                                    )
                                }
                            })
                        }
                        composable("home") {
                            HomePage(
                            navController = navController,
                            homeViewModel = homeViewModel,
                            userData = googleAuthUiClient.getSignedInUser(),
                        ) }
                        composable("category") {
                            CategoryPage(
                                navController = navController,
                                homeViewModel = homeViewModel
                            ) }
                        composable(
                            "categoryEventsPage/{categoryName}",
                            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val categoryName = backStackEntry.arguments?.getString("categoryName")
                            CategoryEventsPage(navController=navController, categoryName = categoryName ?: "", homeViewModel = homeViewModel)
                        }
                        composable("create") {
                            // val createViewModel: CreateViewModel = viewModel(factory = CreateViewModelFactory(homeViewModel))
                            val homeViewModel: HomeViewModel = viewModel()
                            CreatePage(
                                homeViewModel = homeViewModel,
                                navController = navController,
                            ) }
                        composable(
                            "eventDetail/{eventId}",
                            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val eventId = backStackEntry.arguments?.getString("eventId")
                            EventDetailPage(navController=navController, eventId = eventId ?: "", homeViewModel = homeViewModel)
                        }
                        composable("event") {
                            val eventViewModel: EventViewModel = viewModel()
                            EventPage(
                                eventViewModel = eventViewModel,
                                userData = googleAuthUiClient.getSignedInUser()
                            )
                        }
                        composable("settings") {
                            SettingPage(
                                isDarkMode = isDarkMode,
                                onDarkModeToggle = { isDarkMode = it },
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.popBackStack()
                                    }
                                },
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
                            // popUpTo("home") { saveState = true }
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                                // inclusive = false // Don’t pop "home" itself
                            }
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
