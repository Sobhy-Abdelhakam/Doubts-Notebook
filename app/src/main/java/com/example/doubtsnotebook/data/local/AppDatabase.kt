package com.example.doubtsnotebook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CustomerEntity::class, TransactionEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun transactionDao(): TransactionDao
}