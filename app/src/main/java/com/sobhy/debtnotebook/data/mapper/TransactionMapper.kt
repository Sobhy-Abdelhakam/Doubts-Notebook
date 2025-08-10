package com.sobhy.debtnotebook.data.mapper

import com.sobhy.debtnotebook.data.local.entity.TransactionEntity
import com.sobhy.debtnotebook.domain.model.Transaction
import com.sobhy.debtnotebook.domain.model.TransactionType

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