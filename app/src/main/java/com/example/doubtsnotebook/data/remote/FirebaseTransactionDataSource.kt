package com.example.doubtsnotebook.data.remote

import com.example.doubtsnotebook.data.local.entity.TransactionEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseTransactionDataSource(private val firestore: FirebaseFirestore) {
    private val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: throw Exception("User not logged in")

    val transactionsCollection
        get() = firestore.collection("users").document(userId).collection("transactions")

    suspend fun addTransaction(transaction: TransactionEntity): String {
        val doc = transactionsCollection.add(transaction.toMap()).await()
        return doc.id
    }

    suspend fun updateTransaction(remoteId: String, transaction: TransactionEntity) {
        transactionsCollection.document(remoteId).set(transaction.toMap()).await()
    }

    suspend fun fetchAllTransactions(): List<Pair<String, TransactionEntity>> {
        return transactionsCollection.get().await().documents.mapNotNull { doc ->
            val data = doc.toObject(TransactionEntity::class.java)?.copy(
                remoteId = doc.id,
                isSynced = true
            )
            if (data != null) doc.id to data else null
        }
    }

    private fun TransactionEntity.toMap(): Map<String, Any?> = mapOf(
        "customerId" to customerId,
        "type" to type,
        "description" to description,
        "amount" to amount,
        "date" to date,
        "localId" to id
    )
}