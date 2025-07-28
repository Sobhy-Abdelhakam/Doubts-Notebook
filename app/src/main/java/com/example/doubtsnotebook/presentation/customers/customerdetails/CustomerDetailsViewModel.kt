package com.example.doubtsnotebook.presentation.customers.customerdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.doubtsnotebook.CustomerDetails
import com.example.doubtsnotebook.domain.model.Customer
import com.example.doubtsnotebook.domain.model.TransactionType.*
import com.example.doubtsnotebook.domain.repository.CustomerRepository
import com.example.doubtsnotebook.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerDetailsViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val customerRepo: CustomerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val customerId = savedStateHandle.toRoute<CustomerDetails>().customerId

    private val _customer = MutableStateFlow<Customer?>(null)
    val customer = _customer.asStateFlow()

    val transactions = repository.getTransactionsByCustomer(customerId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(), emptyList()
    )

    val balance = transactions.map { list ->
        list.sumOf { txn ->
            when (txn.type) {
                PURCHASE -> txn.amount
                PAYMENT -> -txn.amount
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)

    init {
        viewModelScope.launch {
            val customerData = customerRepo.getCustomerById(customerId)
            _customer.value = customerData
        }
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            customerRepo.deleteCustomer(customer)
        }
    }
}