package com.sobhy.debtnotebook.data.repository

import com.sobhy.debtnotebook.data.local.dao.TransactionDao
import com.sobhy.debtnotebook.data.mapper.toDomain
import com.sobhy.debtnotebook.data.mapper.toEntity
import com.sobhy.debtnotebook.domain.model.Transaction
import com.sobhy.debtnotebook.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(private val dao: TransactionDao) :
    TransactionRepository {
    override fun getTransactionsByCustomer(customerId: Int) =
        dao.getTransactionsByCustomer(customerId).map { it.map { t -> t.toDomain() } }

    override suspend fun addTransaction(transaction: Transaction) {
        dao.insert(transaction.toEntity())
    }

}