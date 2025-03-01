package com.example.auevent

import android.net.Uri
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE) // Prevent Robolectric from looking for AndroidManifest.xml
class ExampleUnitTest {
    private fun isFormComplete(title: String, description: String, category: String?, date: String, time: String, imageUri: Uri?): Boolean {
        return title.isNotBlank() && description.isNotBlank() &&
                category != null && date.isNotBlank() &&
                time.isNotBlank() && imageUri != null
    }

    @Test
    fun formCompletion_isValid() {
        val imageUri = Uri.parse("file://image.jpg")
        assertTrue(isFormComplete("Event", "Description", "Travel and Outdoor", "12/12/2025", "10:00", imageUri))
        assertTrue(isFormComplete("Christmas", "Celebrates Christmas", "Social Activities", "10/12/2025", "12:00", imageUri))
    }


}
