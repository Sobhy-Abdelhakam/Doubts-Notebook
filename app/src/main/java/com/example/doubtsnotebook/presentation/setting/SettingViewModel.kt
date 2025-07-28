package com.example.doubtsnotebook.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubtsnotebook.data.backup.BackupManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val backupManager: BackupManager
): ViewModel() {
    fun syncData(){
        viewModelScope.launch(Dispatchers.IO) {
            backupManager.backupData()
        }
    }
}