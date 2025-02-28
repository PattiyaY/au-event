package com.example.auevent

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import com.example.auevent.pages.CreatePage
import com.example.auevent.viewmodel.HomeViewModel
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreatePageTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var navController: NavController
    private lateinit var context: Context

    @Before
    fun setUp() {
        homeViewModel = mockk(relaxed = true)
        navController = mockk(relaxed = true)
        context = mockk(relaxed = true)
    }

    @Test
    fun testFormValidation_ShowErrorOnEmptyFields() {
        composeTestRule.setContent {
            CreatePage(homeViewModel = homeViewModel, navController = navController)
        }

        // Click the Publish button without filling out the form
        composeTestRule.onNodeWithText("Publish").performClick()

        // Check if error message appears
        composeTestRule.onNodeWithText("⚠️ Fill up all fields").assertIsDisplayed()
    }

    @Test
    fun testSuccessfulEventCreation_NavigatesToHome() {
        val mockUri = mockk<Uri>(relaxed = true)

        composeTestRule.setContent {
            CreatePage(homeViewModel = homeViewModel, navController = navController)
        }

        // Fill in the form
        composeTestRule.onNodeWithText("Enter event name").performTextInput("Test Event")
        composeTestRule.onNodeWithText("Create a new event").performTextInput("Test Description")

        // Select a category
        composeTestRule.onNodeWithText("Social Activities").performClick()

        // Set date and time manually in state (mocking UI interactions)
        composeTestRule.runOnIdle {
            val selectedDate = mutableStateOf("01/01/2025")
            val selectedTime = mutableStateOf("12:00")
        }

        // Simulate image selection
        composeTestRule.runOnIdle {
            val selectedImageUri = mutableStateOf(mockUri)
        }

        // Click Publish
        composeTestRule.onNodeWithText("Publish").performClick()

        // Verify that event creation was triggered
        verify { homeViewModel.createEvent(any(), any(), any(), any()) }

        // Verify navigation to home
        verify { navController.navigate("home") }
    }
}
