package com.example.doubtsnotebook.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.doubtsnotebook.data.local.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: CustomerEntity): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(customers: List<CustomerEntity>)
    @Update
    suspend fun update(customer: CustomerEntity)
    @Delete
    suspend fun delete(customer: CustomerEntity)
    @Query("DELETE FROM customers")
    suspend fun deleteAll()
    @Query("SELECT * FROM customers")
    fun getAllCustomers(): Flow<List<CustomerEntity>>
    @Query("SELECT * FROM customers WHERE id = :id")
    suspend fun getCustomerById(id: Int): CustomerEntity
    @Query("SELECT * FROM customers WHERE name LIKE '%' || :name || '%'")
    fun getCustomerByName(name: String): Flow<List<CustomerEntity>>
//    @Query("SELECT * FROM customers WHERE isSynced = 0")
//    suspend fun getUnsyncedCustomers(): List<CustomerEntity>
//    @Query("SELECT * FROM customers WHERE remoteId = :remoteId LIMIT 1")
//    suspend fun getByRemoteId(remoteId: String): CustomerEntity?
}