package com.sobhy.debtnotebook.presentation.customers.addtransaction

import com.sobhy.debtnotebook.domain.model.TransactionType

data class AddTransactionState(
    val amount: String = "",
    val description: String = "",
    val type: TransactionType = TransactionType.PURCHASE,
    val isAmountError: Boolean = false,
    val isSaved: Boolean = false
)
