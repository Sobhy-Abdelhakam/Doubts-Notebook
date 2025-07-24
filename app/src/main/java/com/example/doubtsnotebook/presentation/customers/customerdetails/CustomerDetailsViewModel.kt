package com.example.doubtsnotebook.presentation.customers.customerdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.doubtsnotebook.CustomerDetails
import com.example.doubtsnotebook.domain.model.TransactionType.*
import com.example.doubtsnotebook.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CustomerDetailsViewModel @Inject constructor(
    private val repository: TransactionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val customerId = savedStateHandle.toRoute<CustomerDetails>().customerId

    val transactions = repository.getTransactionsByCustomer(customerId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val balance = transactions.map { list ->
        list.sumOf { txn ->
            when (txn.type) {
                PURCHASE -> txn.amount
                PAYMENT -> -txn.amount
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
}