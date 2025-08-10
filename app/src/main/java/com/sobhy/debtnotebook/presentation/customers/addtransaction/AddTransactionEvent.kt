package com.sobhy.debtnotebook.presentation.customers.addtransaction

import com.sobhy.debtnotebook.domain.model.TransactionType

sealed class AddTransactionEvent {
    data class OnAmountChanged(val amount: String) : AddTransactionEvent()
    data class OnDescriptionChanged(val description: String) : AddTransactionEvent()
    data class OnTypeChanged(val type: TransactionType) : AddTransactionEvent()
    object OnSaveClicked : AddTransactionEvent()
    object OnSaveHandled : AddTransactionEvent()
}