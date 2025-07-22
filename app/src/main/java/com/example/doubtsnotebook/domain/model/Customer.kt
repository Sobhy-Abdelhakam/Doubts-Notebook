package com.example.doubtsnotebook.domain.model

data class Customer(
    val id: Int = 0,
    val name: String,
    val phone: String? = null,
    val notes: String? = null
)
