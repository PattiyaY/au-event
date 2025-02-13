package com.example.auevent.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.util.UUID


class StorageUtil {

    fun uploadToStorage(uri: Uri, context: Context, type: String) {
        val storage = Firebase.storage
        val storageRef = storage.reference

        val unique_image_name = UUID.randomUUID()
        var spaceRef: StorageReference

        if (type == "image") {
            spaceRef = storageRef.child("images/$unique_image_name.jpg")
        } else {
            spaceRef = storageRef.child("videos/$unique_image_name.mp4")
        }

        val byteArray: ByteArray? = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }


        byteArray?.let {
            var uploadTask = spaceRef.putBytes(byteArray)
            uploadTask.addOnFailureListener{
                Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener { taskSnapshot ->
                Toast.makeText(context, "Upload Successful", Toast.LENGTH_SHORT).show()
            }
        }
    }
}