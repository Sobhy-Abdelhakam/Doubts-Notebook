package com.sobhy.debtnotebook.data.mapper

import com.sobhy.debtnotebook.data.local.entity.CustomerEntity
import com.sobhy.debtnotebook.domain.model.Customer

fun CustomerEntity.toDomain() = Customer(id, name, phone, notes)
fun Customer.toEntity() = CustomerEntity(id = id, name = name, phone = phone, notes = notes)