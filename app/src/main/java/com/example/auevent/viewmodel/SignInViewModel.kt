package com.example.auevent.viewmodel

import androidx.compose.runtime.isTraceInProgress
import androidx.lifecycle.ViewModel
import com.example.auevent.SignInState
import com.example.auevent.model.SignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}