package com.example.doubtsnotebook.presentation.customers.addcustomer

sealed class AddCustomerEvent {
    data class OnNameChange(val name: String): AddCustomerEvent()
    data class OnPhoneChange(val phone: String): AddCustomerEvent()
    data class OnNotesChange(val notes: String): AddCustomerEvent()
    data object OnSaveClicked : AddCustomerEvent()
    data object OnSaveHandled : AddCustomerEvent()
}