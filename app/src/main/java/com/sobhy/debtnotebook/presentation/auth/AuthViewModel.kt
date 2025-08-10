package com.sobhy.debtnotebook.presentation.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobhy.debtnotebook.data.local.AppDatabase
import com.sobhy.debtnotebook.data.local.dao.CustomerDao
import com.sobhy.debtnotebook.data.local.dao.TransactionDao
import com.sobhy.debtnotebook.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val customerDao: CustomerDao,
    private val transactionDao: TransactionDao,
    private val appDatabase: AppDatabase
): ViewModel() {
    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    init {
        Log.d("AuthViewModel", "Auth Created")
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            authRepository.register(email, password).onSuccess {
                authState = AuthState.RegisterSuccess
            }.onFailure { e ->
                authState = AuthState.Error(e.message ?: "Registration failed")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            authRepository.login(email, password).onSuccess {
                authState = AuthState.LoginSuccess
            }.onFailure { e ->
                authState = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            customerDao.deleteAll()
            transactionDao.deleteAll()
            authRepository.logout()
            authState = AuthState.Idle
        }
    }

    fun getCurrentUserId() = authRepository.getCurrentUserId()
}