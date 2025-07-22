package com.example.doubtsnotebook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.doubtsnotebook.data.local.dao.CustomerDao
import com.example.doubtsnotebook.data.local.dao.TransactionDao
import com.example.doubtsnotebook.data.local.entity.CustomerEntity
import com.example.doubtsnotebook.data.local.entity.TransactionEntity

@Database(entities = [CustomerEntity::class, TransactionEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun transactionDao(): TransactionDao
}