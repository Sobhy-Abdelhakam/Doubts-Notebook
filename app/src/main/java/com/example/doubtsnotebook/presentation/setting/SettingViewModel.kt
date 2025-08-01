package com.example.doubtsnotebook.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubtsnotebook.data.backup.BackupManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val backupManager: BackupManager
): ViewModel() {
    private val _uiState = MutableStateFlow<SettingUiState>(SettingUiState())
    val uiState = _uiState.asStateFlow()
    fun backupNow(){
        viewModelScope.launch {
            _uiState.update { it.copy(isBackingUp = true, backupSuccess = null) }

            try {
                backupManager.backupData()
                _uiState.update {
                    it.copy(
                        isBackingUp = false,
                        backupSuccess = true
                    )
                }
            } catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isBackingUp = false,
                        backupSuccess = false
                    )
                }
            }
        }
    }
    fun clearBackupStatus() {
        _uiState.update { it.copy(isBackingUp = false, backupSuccess = null) }
    }
}