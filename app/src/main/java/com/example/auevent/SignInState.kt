package com.example.auevent

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)