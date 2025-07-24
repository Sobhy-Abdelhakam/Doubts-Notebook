package com.example.doubtsnotebook.presentation.customers.addtransaction

import com.example.doubtsnotebook.domain.model.TransactionType

data class AddTransactionState(
    val amount: String = "",
    val description: String = "",
    val type: TransactionType = TransactionType.PURCHASE,
    val isAmountError: Boolean = false,
    val isSaved: Boolean = false
)
