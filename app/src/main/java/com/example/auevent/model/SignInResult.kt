package com.example.auevent.model

data class SignInResult(
    val data: com.example.auevent.model.UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUrl: String?
)