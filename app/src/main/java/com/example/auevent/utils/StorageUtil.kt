package com.example.auevent.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.util.UUID

class StorageUtil {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    suspend fun uploadToStorage(uri: Uri, context: Context, type: String): String? {
        val storageRef = storage.reference
        val uniqueImageName = UUID.randomUUID().toString()

        val spaceRef: StorageReference = when (type) {
            "image" -> storageRef.child("images/$uniqueImageName.jpg")
            else -> storageRef.child("videos/$uniqueImageName.mp4")
        }

        return try {
            val uploadTask = spaceRef.putFile(uri).await()
            val downloadUrl = spaceRef.downloadUrl.await().toString() // Get the download URL
            downloadUrl
        } catch (e: Exception) {
            Toast.makeText(context, "Upload Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            null
        }
    }
}
