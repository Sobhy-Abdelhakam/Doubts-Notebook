package com.sobhy.debtnotebook.presentation.customers.customerList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobhy.debtnotebook.domain.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(private val repository: CustomerRepository) :
    ViewModel() {

    val customers = repository.getAllCustomers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}