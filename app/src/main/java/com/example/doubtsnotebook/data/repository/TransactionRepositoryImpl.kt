package com.example.doubtsnotebook.data.repository

import com.example.doubtsnotebook.data.local.dao.TransactionDao
import com.example.doubtsnotebook.data.mapper.toDomain
import com.example.doubtsnotebook.data.mapper.toEntity
import com.example.doubtsnotebook.domain.model.Transaction
import com.example.doubtsnotebook.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(private val dao: TransactionDao) :
    TransactionRepository {
    override fun getTransactionsByCustomer(customerId: Int) =
        dao.getTransactionsByCustomer(customerId).map { it.map { t -> t.toDomain() } }

    override suspend fun addTransaction(transaction: Transaction) {
        dao.insert(transaction.toEntity())
    }

}