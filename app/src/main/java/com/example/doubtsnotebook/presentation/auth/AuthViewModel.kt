package com.example.doubtsnotebook.presentation.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubtsnotebook.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    init {
        Log.d("AuthViewModel", "Auth Created")
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            authState = when (authRepository.register(email, password).isSuccess) {
                true -> AuthState.Success
                false -> AuthState.Error("Registration failed")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            authState = when (authRepository.login(email, password).isSuccess) {
                true -> AuthState.Success
                false -> AuthState.Error("Login failed")
            }
        }
    }

    fun logout() {
        authRepository.logout()
        authState = AuthState.Idle
    }

    fun getCurrentUserId() = authRepository.getCurrentUserId()
}