package com.example.auevent.repository

import com.example.auevent.model.Category

class CategoryRepository {
    suspend fun getCategories(): List<Category> {
        return try {
            val response = RetrofitInstance.api.getCategories() // API call
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList() // Extract list from response
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList() // Return empty list if error occurs
        }
    }
}
