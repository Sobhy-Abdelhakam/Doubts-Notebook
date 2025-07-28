package com.example.doubtsnotebook.data.repository

import com.example.doubtsnotebook.data.local.dao.CustomerDao
import com.example.doubtsnotebook.data.mapper.toDomain
import com.example.doubtsnotebook.data.mapper.toEntity
import com.example.doubtsnotebook.domain.model.Customer
import com.example.doubtsnotebook.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.map

class CustomerRepositoryImpl(private val dao: CustomerDao) :
    CustomerRepository {
    override fun getAllCustomers() = dao.getAllCustomers().map { it.map { c -> c.toDomain() } }

    override suspend fun addCustomer(customer: Customer) {
        dao.insert(customer.toEntity())
    }

    override suspend fun deleteCustomer(customer: Customer) {
        dao.delete(customer.toEntity())
    }

    override suspend fun getCustomerById(id: Int) = dao.getCustomerById(id).toDomain()

    override fun getCustomerByName(name: String) =
        dao.getCustomerByName(name).map { it.map { c -> c.toDomain() } }
}