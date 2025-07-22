package com.example.doubtsnotebook.di

import android.content.Context
import androidx.room.Room
import com.example.doubtsnotebook.data.local.AppDatabase
import com.example.doubtsnotebook.data.local.dao.CustomerDao
import com.example.doubtsnotebook.data.local.dao.TransactionDao
import com.example.doubtsnotebook.data.repository.CustomerRepositoryImpl
import com.example.doubtsnotebook.data.repository.TransactionRepositoryImpl
import com.example.doubtsnotebook.domain.repository.CustomerRepository
import com.example.doubtsnotebook.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            klass = AppDatabase::class.java,
            "doubts_manager_db"
        ).build()

    @Provides
    fun provideCustomerDao(db: AppDatabase) = db.customerDao()
    @Provides
    fun provideTransactionDao(db: AppDatabase) = db.transactionDao()

    @Provides
    fun provideCustomerRepo(dao: CustomerDao): CustomerRepository =
        CustomerRepositoryImpl(dao)

    @Provides
    fun provideTransactionRepo(dao: TransactionDao): TransactionRepository =
        TransactionRepositoryImpl(dao)
}