package com.example.doubtsnotebook.presentation.restorebackup

data class RestoreUiState(
    val showDialog: Boolean = false,
    val isRestoring: Boolean = false,
    val done: Boolean = false
)