package com.example.doubtsnotebook.presentation.restorebackup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RestoreBackupScreen(
    viewModel: RestoreViewModel = hiltViewModel(),
    onDone: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    when {
        state.showDialog -> {
            AlertDialog(
                onDismissRequest = { viewModel.skipRestore() },
                title = { Text("استعادة النسخة الاحتياطية") },
                text = { Text("هل ترغب في استعادة بياناتك؟") },
                confirmButton = {
                    Button(onClick = { viewModel.restore() }) {
                        Text("نعم")
                    }
                },
                dismissButton = {
                    Button(onClick = { viewModel.skipRestore() }) {
                        Text("لا")
                    }
                }
            )
        }

        state.isRestoring -> {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.done -> {
            LaunchedEffect(Unit) {
                onDone()
            }
        }
    }
}