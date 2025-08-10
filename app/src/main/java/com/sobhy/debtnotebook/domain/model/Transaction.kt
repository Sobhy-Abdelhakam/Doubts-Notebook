package com.sobhy.debtnotebook.domain.model

enum class TransactionType { PURCHASE, PAYMENT }
data class Transaction(
    val id: Int = 0,
    val customerId: Int,
    val type: TransactionType,
    val description: String? = null,
    val amount: Double,
    val date: Long = System.currentTimeMillis(),
)
