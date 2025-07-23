package com.example.doubtsnotebook.presentation.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubtsnotebook.domain.model.Customer
import com.example.doubtsnotebook.domain.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(private val repository: CustomerRepository): ViewModel() {
    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers = _customers.asStateFlow()

    init {
        getAllCustomers()
    }

    private fun getAllCustomers() {
        viewModelScope.launch {
            repository.getAllCustomers()
                .collect { customerList ->
                    _customers.value = customerList
                }
        }
    }
}