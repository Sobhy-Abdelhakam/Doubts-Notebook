package com.example.doubtsnotebook.presentation.restorebackup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubtsnotebook.data.backup.BackupManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestoreViewModel @Inject constructor(
    private val backupManager: BackupManager
): ViewModel() {
    private val _uiState = MutableStateFlow(RestoreUiState(showDialog = true))
    val uiState: StateFlow<RestoreUiState> = _uiState

    fun restore() {
        viewModelScope.launch {
            _uiState.update { it.copy(showDialog = false, isRestoring = true) }
            backupManager.restoreBackup()
            _uiState.update { it.copy(isRestoring = false, done = true) }
        }
    }

    fun skipRestore() {
        _uiState.update { it.copy(showDialog = false, done = true) }
    }
}