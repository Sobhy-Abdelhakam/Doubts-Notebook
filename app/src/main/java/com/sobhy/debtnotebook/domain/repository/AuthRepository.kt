package com.sobhy.debtnotebook.domain.repository

interface AuthRepository {
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    fun logout()
    fun getCurrentUserId(): String?
}