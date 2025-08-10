package com.sobhy.debtnotebook.domain.repository

import com.sobhy.debtnotebook.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactionsByCustomer(customerId: Int): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
}