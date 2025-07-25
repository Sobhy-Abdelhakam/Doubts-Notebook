package com.example.doubtsnotebook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteId: String? = null,
    val name: String,
    val phone: String?,
    val notes: String?,
    val isSynced: Boolean = false
)
