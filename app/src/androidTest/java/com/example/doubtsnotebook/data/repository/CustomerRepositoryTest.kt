package com.example.doubtsnotebook.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.doubtsnotebook.data.local.AppDatabase
import com.example.doubtsnotebook.data.local.dao.CustomerDao
import com.example.doubtsnotebook.data.local.entity.CustomerEntity
import com.example.doubtsnotebook.domain.model.Customer
import com.example.doubtsnotebook.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class CustomerRepositoryTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: CustomerDao
    private lateinit var repository: CustomerRepository
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        dao = database.customerDao()
        repository = CustomerRepositoryImpl(dao)
    }
    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addCustomer_shouldBeStoredAndRetrieved() = runTest {
        val customer = Customer(
            id = 1,
            name = "John Doe",
            phone = "011222",
            notes = "New Customer"
        )
        repository.addCustomer(customer)

        val all = repository.getAllCustomers().first()
        assert(all.size == 1)
        assert(all.first().name == "John Doe")
    }

    @Test
    fun getCustomersThatHaveNameContainTheString() = runTest {
        val customer1 = Customer(id = 1, name = "John Doe", phone = "1234567890", notes = "Test customer 1")
        val customer2 = Customer(id = 2, name = "Jane Doe", phone = "0987654321", notes = "Test customer 2")
        repository.addCustomer(customer1)
        repository.addCustomer(customer2)

        val customersContainA = repository.getCustomerByName("a").first()
        assert(customersContainA.size == 1)
        assert(customersContainA.first() == customer2)
    }
}