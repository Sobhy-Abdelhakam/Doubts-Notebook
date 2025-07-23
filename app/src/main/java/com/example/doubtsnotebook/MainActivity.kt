package com.example.doubtsnotebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doubtsnotebook.presentation.customers.CustomerListScreen
import com.example.doubtsnotebook.presentation.customers.addcustomer.AddCustomerScreen
import com.example.doubtsnotebook.ui.theme.DoubtsNotebookTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoubtsNotebookTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = CustomerList) {
                    composable<CustomerList> {
                        CustomerListScreen(onAddCustomerClick = { navController.navigate(AddCustomer) })
                    }
                    composable<AddCustomer> { AddCustomerScreen(onBack = { navController.popBackStack() }) }
                }
            }
        }
    }
}

@Serializable
object CustomerList

@Serializable
object AddCustomer