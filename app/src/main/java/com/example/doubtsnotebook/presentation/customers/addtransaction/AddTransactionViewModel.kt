package com.example.doubtsnotebook.presentation.customers.addtransaction

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.doubtsnotebook.domain.model.Transaction
import com.example.doubtsnotebook.domain.repository.TransactionRepository
import com.example.doubtsnotebook.presentation.navigation.AddTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: TransactionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val customerId: Int = savedStateHandle.toRoute<AddTransaction>().customerId

    private val _state = MutableStateFlow(AddTransactionState())
    val state = _state.asStateFlow()

    fun onEvent(event: AddTransactionEvent){
        when(event){
            is AddTransactionEvent.OnAmountChanged -> {
                _state.update { it.copy(amount = event.amount, isAmountError = false) }
            }
            is AddTransactionEvent.OnDescriptionChanged -> {
                _state.update { it.copy(description = event.description) }
            }
            is AddTransactionEvent.OnTypeChanged -> {
                _state.update { it.copy(type = event.type) }
            }
            AddTransactionEvent.OnSaveClicked -> {
                val amount = _state.value.amount.toDoubleOrNull()
                if (amount == null || amount <= 0.0) {
                    _state.update { it.copy(isAmountError = true) }
                    return
                }

                val txn = Transaction(
                    customerId = customerId,
                    type = _state.value.type,
                    description = _state.value.description.takeIf { it.isNotBlank() },
                    amount = amount,
                    date = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    repository.addTransaction(txn)
                    _state.update { it.copy(isSaved = true) }
                }
            }
            AddTransactionEvent.OnSaveHandled -> {
                _state.update { it.copy(isSaved = false) }
            }
        }
    }
}