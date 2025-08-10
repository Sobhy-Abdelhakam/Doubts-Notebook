package com.sobhy.debtnotebook.presentation.customers.addcustomer

data class AddCustomerState(
    val name: String = "",
    val phone: String = "",
    val notes: String = "",
    val isNameError: Boolean = false,
    val isSaved: Boolean = false,
)
