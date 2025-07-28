package com.example.doubtsnotebook.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubtsnotebook.data.sync.CustomerSyncManager
import com.example.doubtsnotebook.data.sync.TransactionSyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val customerSyncManager: CustomerSyncManager,
    private val transactionSyncManager: TransactionSyncManager,
): ViewModel() {
    fun syncData(){
        viewModelScope.launch {
            customerSyncManager.sync()
            transactionSyncManager.sync()
        }
    }
}