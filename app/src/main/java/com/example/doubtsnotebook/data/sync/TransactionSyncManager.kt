package com.example.doubtsnotebook.data.sync

import com.example.doubtsnotebook.data.local.dao.TransactionDao
import com.example.doubtsnotebook.data.remote.FirebaseTransactionDataSource

class TransactionSyncManager(
    private val transactionDao: TransactionDao,
    private val firebaseDataSource: FirebaseTransactionDataSource
) {
    suspend fun sync() {
        uploadLocalUnsyncedTransactions()
        downloadRemoteTransactions()
    }

    // 🔼 Room → Firestore
    private suspend fun uploadLocalUnsyncedTransactions() {
        val unsyncedTransactions = transactionDao.getUnsyncedTransactions()
        for (transaction in unsyncedTransactions) {
            try {
                val remoteId = if (transaction.remoteId == null) {
                    firebaseDataSource.addTransaction(transaction)
                } else {
                    firebaseDataSource.updateTransaction(transaction.remoteId, transaction)
                    transaction.remoteId
                }

                val updated = transaction.copy(remoteId = remoteId, isSynced = true)
                transactionDao.updateTransaction(updated)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🔽 Firestore → Room
    private suspend fun downloadRemoteTransactions() {
        try {
            val remoteTransactions = firebaseDataSource.fetchAllTransactions()
            for ((remoteId, remoteTransaction) in remoteTransactions) {
                val local = transactionDao.getByRemoteId(remoteId)
                if (local == null) {
                    transactionDao.insert(remoteTransaction.copy(remoteId = remoteId, isSynced = true))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}