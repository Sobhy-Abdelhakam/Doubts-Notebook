package com.example.doubtsnotebook.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert
    suspend fun insert(customer: CustomerEntity): Long
    @Update
    suspend fun update(customer: CustomerEntity)
    @Delete
    suspend fun delete(customer: CustomerEntity)
    @Query("SELECT * FROM customers")
    fun getAllCustomers(): Flow<List<CustomerEntity>>
    @Query("SELECT * FROM customers WHERE id = :id")
    suspend fun getCustomerById(id: Int): CustomerEntity
    @Query("SELECT * FROM customers WHERE name LIKE '%' || :name || '%'")
    fun getCustomerByName(name: String): Flow<List<CustomerEntity>>
}