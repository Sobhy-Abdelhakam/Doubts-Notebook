package com.example.doubtsnotebook.data.sync

import com.example.doubtsnotebook.data.local.dao.CustomerDao
import com.example.doubtsnotebook.data.remote.FirebaseCustomerDataSource

class CustomerSyncManager(
    private val customerDao: CustomerDao,
    private val firebaseDataSource: FirebaseCustomerDataSource
) {
    suspend fun sync() {
        uploadLocalUnsyncedCustomers()
        downloadRemoteCustomers()
    }

    // 🔼 Upload Room → Firestore
    private suspend fun uploadLocalUnsyncedCustomers() {
        val unsyncedCustomers = customerDao.getUnsyncedCustomers()
        for (customer in unsyncedCustomers) {
            try {
                val remoteId = if (customer.remoteId == null) {
                    firebaseDataSource.addCustomer(customer)
                } else {
                    firebaseDataSource.updateCustomer(customer.remoteId, customer)
                    customer.remoteId
                }
                val updated = customer.copy(remoteId = remoteId, isSynced = true)
                customerDao.update(updated)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    // 🔽 Download Firestore → Room
    private suspend fun downloadRemoteCustomers() {
        try {
            val remoteCustomers = firebaseDataSource.fetchAllCustomers()
            for ((remoteId, remoteCustomer) in remoteCustomers) {
                val local = customerDao.getByRemoteId(remoteId)
                if (local == null) {
                    customerDao.insert(remoteCustomer.copy(remoteId = remoteId, isSynced = true))
                } else {
                    // Optional: handle updates if Firestore is newer
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}