
package com.example.doubtsnotebook.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.doubtsnotebook.data.local.AppDatabase
import com.example.doubtsnotebook.data.local.entity.CustomerEntity
import com.example.doubtsnotebook.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var customerDao: CustomerDao
    private lateinit var transactionDao: TransactionDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        customerDao = database.customerDao()
        transactionDao = database.transactionDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertTransactionAndGetByCustomerId() = runTest {
        val customer = CustomerEntity(id = 1, name = "John Doe", phone = "1234567890", notes = "Test customer")
        customerDao.insert(customer)

        val transaction = TransactionEntity(id = 1, customerId = 1, type = "PURCHASE", description = "Test purchase", amount = 100.0)
        transactionDao.insert(transaction)

        val transactions = transactionDao.getTransactionsByCustomer(1).first()
        assert(transactions.size == 1)
        assert(transactions.contains(transaction))
    }
}
