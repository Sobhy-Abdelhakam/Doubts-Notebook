package com.sobhy.debtnotebook.presentation.customers.addcustomer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobhy.debtnotebook.domain.model.Customer
import com.sobhy.debtnotebook.domain.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCustomerViewModel @Inject constructor(
    private val repository: CustomerRepository
): ViewModel() {
    private val _state = MutableStateFlow(AddCustomerState())
    val state = _state.asStateFlow()
    fun onEvent(event: AddCustomerEvent){
        when(event){
            is AddCustomerEvent.OnNameChange -> {
                _state.update { it.copy(name = event.name, isNameError = false) }
            }
            is AddCustomerEvent.OnPhoneChange -> {
                _state.update { it.copy(phone = event.phone) }
            }
            is AddCustomerEvent.OnNotesChange -> {
                _state.update { it.copy(notes = event.notes) }
            }
            AddCustomerEvent.OnSaveClicked -> {
                if (_state.value.name.isBlank()){
                    _state.update { it.copy(isNameError = true) }
                    return
                }
                val customer = Customer(
                    name = _state.value.name.trim(),
                    phone = _state.value.phone.trim(),
                    notes = _state.value.notes.trim()
                )
                viewModelScope.launch {
                    repository.addCustomer(customer)
                    _state.update { it.copy(isSaved = true) }
                }
            }
            AddCustomerEvent.OnSaveHandled -> {
                _state.update { it.copy(isSaved = false) }
            }
        }
    }
}