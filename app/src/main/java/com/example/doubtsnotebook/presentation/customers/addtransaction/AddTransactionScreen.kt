package com.example.doubtsnotebook.presentation.customers.addtransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.doubtsnotebook.R
import com.example.doubtsnotebook.domain.model.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onBack()
            viewModel.onEvent(AddTransactionEvent.OnSaveHandled)
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_transaction)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = state.type == TransactionType.PURCHASE,
                    onClick = {
                        viewModel.onEvent(AddTransactionEvent.OnTypeChanged(TransactionType.PURCHASE))
                    },
                    label = { Text(stringResource(R.string.purchase)) }
                )
                FilterChip(
                    selected = state.type == TransactionType.PAYMENT,
                    onClick = {
                        viewModel.onEvent(AddTransactionEvent.OnTypeChanged(TransactionType.PAYMENT))
                    },
                    label = { Text(stringResource(R.string.payment)) }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.amount,
                onValueChange = { viewModel.onEvent(AddTransactionEvent.OnAmountChanged(it)) },
                label = { Text(stringResource(R.string.amount)) },
                isError = state.isAmountError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.isAmountError) {
                Text(stringResource(R.string.amount_error), color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.onEvent(AddTransactionEvent.OnDescriptionChanged(it)) },
                label = { Text(stringResource(R.string.note)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onEvent(AddTransactionEvent.OnSaveClicked) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}