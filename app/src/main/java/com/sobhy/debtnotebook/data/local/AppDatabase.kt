package com.sobhy.debtnotebook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sobhy.debtnotebook.data.local.dao.CustomerDao
import com.sobhy.debtnotebook.data.local.dao.TransactionDao
import com.sobhy.debtnotebook.data.local.entity.CustomerEntity
import com.sobhy.debtnotebook.data.local.entity.TransactionEntity

@Database(entities = [CustomerEntity::class, TransactionEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun transactionDao(): TransactionDao
}