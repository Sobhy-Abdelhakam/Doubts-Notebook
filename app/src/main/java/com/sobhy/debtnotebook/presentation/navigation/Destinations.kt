package com.sobhy.debtnotebook.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable object CustomerList
@Serializable object AddCustomer
@Serializable data class CustomerDetails(val customerId: Int)
@Serializable data class AddTransaction(val customerId: Int)
@Serializable object Auth
@Serializable object Setting
@Serializable object Restore