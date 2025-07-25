package com.example.doubtsnotebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.doubtsnotebook.presentation.auth.AuthScreen
import com.example.doubtsnotebook.presentation.auth.AuthViewModel
import com.example.doubtsnotebook.presentation.customers.customerList.CustomerListScreen
import com.example.doubtsnotebook.presentation.customers.addcustomer.AddCustomerScreen
import com.example.doubtsnotebook.presentation.customers.addtransaction.AddTransactionScreen
import com.example.doubtsnotebook.presentation.customers.customerdetails.CustomerDetailsScreen
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
                val viewModel: AuthViewModel = hiltViewModel()
                val startDestination = if (viewModel.getCurrentUserId() != null) CustomerList else Auth
                NavHost(navController = navController, startDestination = startDestination) {
                    composable<Auth> {
                        AuthScreen(viewModel) { navController.navigate(CustomerList){popUpTo(Auth){inclusive = true} } }
                    }
                    composable<CustomerList> {
                        CustomerListScreen(
                            authViewModel = viewModel,
                            onAddCustomerClick = { navController.navigate(AddCustomer) },
                            onCustomerClick = { navController.navigate(CustomerDetails(it)) }
                        )
                    }
                    composable<AddCustomer> { AddCustomerScreen(onBack = { navController.popBackStack() }) }
                    composable<CustomerDetails>(
                        typeMap = mapOf()
                    ) { backStackEntry ->
                        CustomerDetailsScreen(
                            customerId = backStackEntry.toRoute<CustomerDetails>().customerId,
                            onAddTransactionClick = { navController.navigate(AddTransaction(it)) },
                            onBack = {navController.popBackStack()}
                        )
                    }
                    composable<AddTransaction> { AddTransactionScreen(onBack = { navController.popBackStack() }) }
                }
            }
        }
    }
}

@Serializable
object CustomerList

@Serializable
object AddCustomer

@Serializable
data class CustomerDetails(val customerId: Int)

@Serializable
data class AddTransaction(val customerId: Int)
// add auth screen in navigation
@Serializable
object Auth