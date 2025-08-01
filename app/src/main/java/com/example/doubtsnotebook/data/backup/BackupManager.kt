package com.example.doubtsnotebook.data.backup

import android.util.Log
import com.example.doubtsnotebook.data.local.dao.CustomerDao
import com.example.doubtsnotebook.data.local.dao.TransactionDao
import com.example.doubtsnotebook.data.local.entity.CustomerEntity
import com.example.doubtsnotebook.data.local.entity.TransactionEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BackupManager(
    private val customerDao: CustomerDao,
    private val transactionDao: TransactionDao,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    val userId = auth.currentUser?.uid

    suspend fun backupData() {
        if (userId == null) return
        val userRef = firestore.collection("backups").document(userId)
        try {
            withContext(Dispatchers.IO) {
                backupCustomers(userRef)
                backupTransactions(userRef)
            }
            Log.d("BackupManager", "Backup completed successfully.")
        } catch (e: Exception) {
            Log.e("BackupManager", "Backup failed: ${e.message}", e)
        }
    }
    suspend fun restoreBackup() {
        if (userId == null) return
        Log.d("Backup", "Backup data")
        val userRef = firestore.collection("backups").document(userId)

        try {
            withContext(Dispatchers.IO) {
                val customerSnapshots = userRef.collection("customers").get().await()
                val transactionSnapshots = userRef.collection("transactions").get().await()

                val customers = customerSnapshots.toObjects(CustomerEntity::class.java)
                val transactions = transactionSnapshots.toObjects(TransactionEntity::class.java)

                customerDao.insertAll(customers)
                transactionDao.insertAll(transactions)
            }
            Log.d("BackupManager", "Restore completed successfully.")
        } catch (e: Exception) {
            Log.e("BackupManager", "Restore failed: ${e.message}", e)
        }
    }

    private suspend fun backupCustomers(userRef: DocumentReference) {
        val customers = customerDao.getAllCustomers().firstOrNull().orEmpty()

        customers.forEach { customer ->
            try {
                userRef.collection("customers")
                    .document(customer.id.toString())
                    .set(customer)
                    .await()
            } catch (e: Exception) {
                Log.e("BackupManager", "Failed to backup customer ${customer.id}: ${e.message}", e)
            }
        }
    }
    private suspend fun backupTransactions(userRef: DocumentReference) {
        val transactions = transactionDao.getAllTransactions()

        transactions.forEach { transaction ->
            try {
                userRef.collection("transactions")
                    .document(transaction.id.toString())
                    .set(transaction)
                    .await()
            } catch (e: Exception) {
                Log.e("BackupManager", "Failed to backup transaction ${transaction.id}: ${e.message}", e)
            }
        }
    }
}