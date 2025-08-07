package com.example.doubtsnotebook.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubtsnotebook.data.backup.BackupManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
                loadLastBackupTime()
            } catch (_: Exception){
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

    fun loadLastBackupTime() {
        viewModelScope.launch {
            val time = backupManager.getLastBackupTime()
            val formatted = time?.toDate()?.let { formatDate(it) }
            _uiState.update { it.copy(lastBackupAt = formatted) }
        }
    }
    private fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("dd MMM yyyy - hh:mm a", Locale.getDefault())
        return formatter.format(date)
    }

}