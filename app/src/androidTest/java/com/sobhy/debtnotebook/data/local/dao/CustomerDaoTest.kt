
package com.sobhy.debtnotebook.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sobhy.debtnotebook.data.local.AppDatabase
import com.sobhy.debtnotebook.data.local.entity.CustomerEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomerDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var customerDao: CustomerDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        customerDao = database.customerDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCustomerAndGetById() = runTest {
        val customer = CustomerEntity(id = 1, name = "John Doe", phone = "1234567890", notes = "Test customer")
        customerDao.insert(customer)

        val retrievedCustomer = customerDao.getCustomerById(1)
        assert(retrievedCustomer == customer)
    }

    @Test
    fun getAllCustomers() = runTest {
        val customer1 = CustomerEntity(id = 1, name = "John Doe", phone = "1234567890", notes = "Test customer 1")
        val customer2 = CustomerEntity(id = 2, name = "Jane Doe", phone = "0987654321", notes = "Test customer 2")
        customerDao.insert(customer1)
        customerDao.insert(customer2)

        val allCustomers = customerDao.getAllCustomers().first()
        assert(allCustomers.size == 2)
        assert(allCustomers.contains(customer1))
        assert(allCustomers.contains(customer2))
    }

    @Test
    fun updateCustomer() = runTest {
        val customer = CustomerEntity(id = 1, name = "John Doe", phone = "1234567890", notes = "Test customer")
        customerDao.insert(customer)

        val updatedCustomer = customer.copy(name = "John Smith")
        customerDao.update(updatedCustomer)

        val retrievedCustomer = customerDao.getCustomerById(1)
        assert(retrievedCustomer == updatedCustomer)
    }

    @Test
    fun deleteCustomer() = runTest {
        val customer = CustomerEntity(id = 1, name = "John Doe", phone = "1234567890", notes = "Test customer")
        customerDao.insert(customer)
        customerDao.delete(customer)

        val allCustomers = customerDao.getAllCustomers().first()
        assert(allCustomers.isEmpty())
    }

    @Test
    fun getCustomerByName() = runTest {
        val customer1 = CustomerEntity(id = 1, name = "John Doe", phone = "1234567890", notes = "Test customer 1")
        val customer2 = CustomerEntity(id = 2, name = "Jane Doe", phone = "0987654321", notes = "Test customer 2")
        customerDao.insert(customer1)
        customerDao.insert(customer2)

        val customers = customerDao.getCustomerByName("John").first()
        assert(customers.size == 1)
        assert(customers.contains(customer1))
    }
}
