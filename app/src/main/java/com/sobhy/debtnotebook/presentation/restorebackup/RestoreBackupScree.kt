package com.sobhy.debtnotebook.presentation.restorebackup

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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sobhy.debtnotebook.R

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
                title = { Text(stringResource(R.string.restore_backup_title)) },
                text = { Text(stringResource(R.string.restore_backup_subtitle)) },
                confirmButton = {
                    Button(onClick = { viewModel.restore() }) {
                        Text(stringResource(R.string.yes))
                    }
                },
                dismissButton = {
                    Button(onClick = { viewModel.skipRestore() }) {
                        Text(stringResource(R.string.no))
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