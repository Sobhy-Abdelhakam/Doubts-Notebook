package com.example.doubtsnotebook.data.remote

import com.example.doubtsnotebook.data.local.entity.CustomerEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseCustomerDataSource(
    private val firestore: FirebaseFirestore
) {
    private val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: throw Exception("User not logged in")

    private val customersCollection = firestore.collection("users").document(userId).collection("customers")

    suspend fun addCustomer(customer: CustomerEntity): String {
        val doc = customersCollection.add(customer.toMap()).await()
        return doc.id
    }

    suspend fun updateCustomer(remoteId: String, customer: CustomerEntity) {
        customersCollection.document(remoteId).set(customer.toMap()).await()
    }

    suspend fun fetchAllCustomers(): List<Pair<String, CustomerEntity>> {
        return customersCollection.get().await().documents.mapNotNull { doc ->
            val data = doc.toObject(CustomerEntity::class.java)?.copy(
                remoteId = doc.id,
                isSynced = true
            )
            if (data != null) doc.id to data else null
        }
    }

    private fun CustomerEntity.toMap(): Map<String, Any?> = mapOf(
        "name" to name,
        "phone" to phone,
        "notes" to notes,
        "localId" to id
    )
}