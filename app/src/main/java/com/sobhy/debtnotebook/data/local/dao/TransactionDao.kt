package com.sobhy.debtnotebook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sobhy.debtnotebook.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity): Long
    @Insert
    suspend fun insertAll(transactions: List<TransactionEntity>)
    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)
    @Query("DELETE FROM transactions")
    suspend fun deleteAll()
    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<TransactionEntity>
    @Query("SELECT * FROM transactions WHERE customerId = :customerId ORDER BY date DESC")
    fun getTransactionsByCustomer(customerId: Int): Flow<List<TransactionEntity>>
}