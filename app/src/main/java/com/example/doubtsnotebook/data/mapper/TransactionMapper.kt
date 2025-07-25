package com.example.doubtsnotebook.data.mapper

import com.example.doubtsnotebook.data.local.entity.TransactionEntity
import com.example.doubtsnotebook.domain.model.Transaction
import com.example.doubtsnotebook.domain.model.TransactionType

fun TransactionEntity.toDomain() =
    Transaction(id, customerId, TransactionType.valueOf(type), description, amount, date)

fun Transaction.toEntity() = TransactionEntity(
    id = id,
    customerId = customerId,
    type = type.name,
    description = description,
    amount = amount,
    date = date
)