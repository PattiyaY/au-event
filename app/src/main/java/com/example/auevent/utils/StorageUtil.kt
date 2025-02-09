package com.example.auevent.utils

import android.content.Context
import android.net.Uri


class StorageUtil {

    fun uploadToStorage(uri: Uri, context: Context, type: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
    }
}