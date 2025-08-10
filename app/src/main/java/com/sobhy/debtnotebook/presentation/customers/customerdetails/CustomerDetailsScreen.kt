package com.sobhy.debtnotebook.presentation.customers.customerdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sobhy.debtnotebook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailsScreen(
    viewModel: CustomerDetailsViewModel = hiltViewModel(),
    customerId: Int,
    onAddTransactionClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val customer by viewModel.customer.collectAsState()
    val transaction by viewModel.transactions.collectAsState()
    val balance by viewModel.balance.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(customer?.name ?: "") },
                actions = {
                    IconButton(onClick = {
                        customer?.let { c ->
                            viewModel.deleteCustomer(c)
                            onBack()
                        }

                    }) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = stringResource(R.string.delete_customer),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddTransactionClick(customerId) }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_transaction))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("${stringResource(R.string.balance)}: $balance", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text("${stringResource(R.string.transactions)}: ", style = MaterialTheme.typography.titleMedium)

            if (transaction.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(R.string.no_transactions))
            } else {
                LazyColumn {
                    items(transaction, key = { it.id }) { txn ->
                        TransactionItem(txn)
                    }
                }
            }
        }
    }
}