package com.example.doubtsnotebook.presentation.customers.customerList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.doubtsnotebook.R
import com.example.doubtsnotebook.presentation.auth.AuthViewModel
import com.example.doubtsnotebook.presentation.customers.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerListScreen(
    viewModel: CustomerViewModel = hiltViewModel(),
    authViewModel: AuthViewModel,
    onAddCustomerClick: () -> Unit,
    onCustomerClick: (Int) -> Unit
) {
    val customers by viewModel.customers.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.customers)) },
                actions = {
                    IconButton(onClick = authViewModel::logout) {
                        Icon(painterResource(R.drawable.logout), contentDescription = "Logout")
                    }
                }
            )
                 },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCustomerClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_customer))
            }
        }
    ) { padding ->
        if (customers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.no_customers))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        placeholder = {
                            Text(stringResource(R.string.search))
                        },
                        shape = RoundedCornerShape(16.dp)
                        )
                }
                items(
                    customers.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    },
                    key = { it.id }
                ) { customer ->
                    CustomerItem(customer, onCustomerClick = onCustomerClick)
                }
            }
        }
    }
}