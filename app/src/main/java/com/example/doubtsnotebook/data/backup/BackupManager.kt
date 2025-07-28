package com.example.doubtsnotebook.data.backup

import com.example.doubtsnotebook.data.local.dao.CustomerDao
import com.example.doubtsnotebook.data.local.dao.TransactionDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first

class BackupManager(
    private val customerDao: CustomerDao,
    private val transactionDao: TransactionDao,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {


    suspend fun backupData() {
        val userId = auth.currentUser?.uid ?: return
        val userRef = firestore.collection("backups").document(userId)
        backupCustomers(userRef)
        backupTransactions(userRef)
    }

    private suspend fun backupCustomers(userRef: DocumentReference) {
        val customers = customerDao.getAllCustomers().first()

        userRef.collection("customers").apply {
            customers.forEach {
                document(it.id.toString()).set(it)
            }
        }
    }
    private suspend fun backupTransactions(userRef: DocumentReference) {
        val transactions = transactionDao.getAllTransactions()
        userRef.collection("transactions").apply {
            transactions.forEach {
                document(it.id.toString()).set(it)
            }
        }
    }
}