package com.sobhy.debtnotebook.domain.repository

import com.sobhy.debtnotebook.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getAllCustomers(): Flow<List<Customer>>
    suspend fun addCustomer(customer: Customer)
    suspend fun deleteCustomer(customer: Customer)
    suspend fun getCustomerById(id: Int): Customer
    fun getCustomerByName(name: String): Flow<List<Customer>>
}