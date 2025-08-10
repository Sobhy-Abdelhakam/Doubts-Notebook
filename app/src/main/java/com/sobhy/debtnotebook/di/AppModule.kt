package com.sobhy.debtnotebook.di

import android.content.Context
import androidx.room.Room
import com.sobhy.debtnotebook.data.backup.BackupManager
import com.sobhy.debtnotebook.data.local.AppDatabase
import com.sobhy.debtnotebook.data.local.dao.CustomerDao
import com.sobhy.debtnotebook.data.local.dao.TransactionDao
import com.sobhy.debtnotebook.data.repository.AuthRepositoryImpl
import com.sobhy.debtnotebook.data.repository.CustomerRepositoryImpl
import com.sobhy.debtnotebook.data.repository.TransactionRepositoryImpl
import com.sobhy.debtnotebook.domain.repository.AuthRepository
import com.sobhy.debtnotebook.domain.repository.CustomerRepository
import com.sobhy.debtnotebook.domain.repository.TransactionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepo(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideBackupManager(
        customerDao: CustomerDao,
        transactionDao: TransactionDao,
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): BackupManager = BackupManager(
        customerDao = customerDao,
        transactionDao = transactionDao,
        firestore = firestore,
        auth = auth,
    )
}