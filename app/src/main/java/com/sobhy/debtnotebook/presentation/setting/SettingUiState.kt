package com.sobhy.debtnotebook.presentation.setting

data class SettingUiState(
    val isBackingUp: Boolean = false,
    val backupSuccess: Boolean? = null,
    val lastBackupAt: String? = null
)
