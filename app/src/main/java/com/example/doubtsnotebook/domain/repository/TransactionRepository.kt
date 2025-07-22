package com.example.doubtsnotebook.domain.repository

import com.example.doubtsnotebook.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactionsByCustomer(customerId: Int): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
}