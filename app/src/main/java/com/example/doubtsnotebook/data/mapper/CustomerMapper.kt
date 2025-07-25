package com.example.doubtsnotebook.data.mapper

import com.example.doubtsnotebook.data.local.entity.CustomerEntity
import com.example.doubtsnotebook.domain.model.Customer

fun CustomerEntity.toDomain() = Customer(id, name, phone, notes)
fun Customer.toEntity() = CustomerEntity(id = id, name = name, phone = phone, notes = notes)