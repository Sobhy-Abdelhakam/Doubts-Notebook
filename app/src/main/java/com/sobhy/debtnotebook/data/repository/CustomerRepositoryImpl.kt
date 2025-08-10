package com.sobhy.debtnotebook.data.repository

import com.sobhy.debtnotebook.data.local.dao.CustomerDao
import com.sobhy.debtnotebook.data.mapper.toDomain
import com.sobhy.debtnotebook.data.mapper.toEntity
import com.sobhy.debtnotebook.domain.model.Customer
import com.sobhy.debtnotebook.domain.repository.CustomerRepository
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