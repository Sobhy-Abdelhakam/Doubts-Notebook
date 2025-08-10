package com.example.doubtsnotebook.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.doubtsnotebook.presentation.auth.AuthScreen
import com.example.doubtsnotebook.presentation.auth.AuthViewModel
import com.example.doubtsnotebook.presentation.config.LanguageManager
import com.example.doubtsnotebook.presentation.customers.addcustomer.AddCustomerScreen
import com.example.doubtsnotebook.presentation.customers.addtransaction.AddTransactionScreen
import com.example.doubtsnotebook.presentation.customers.customerList.CustomerListScreen
import com.example.doubtsnotebook.presentation.customers.customerdetails.CustomerDetailsScreen
import com.example.doubtsnotebook.presentation.restorebackup.RestoreBackupScreen
import com.example.doubtsnotebook.presentation.setting.SettingScreen
import com.example.doubtsnotebook.ui.theme.ThemeManager

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val startDestination = if (authViewModel.getCurrentUserId() != null) CustomerList else Auth

    NavHost(navController = navController, startDestination = startDestination) {
        composable<Auth> {
            AuthScreen(
                authViewModel,
                navigateToRestoreBackup = { navController.navigate(Restore) },
                navigateToCustomerList = { navController.navigate(CustomerList) }
            )
        }
        composable<CustomerList> {
            CustomerListScreen(
                onAddCustomerClick = { navController.navigate(AddCustomer) },
                onCustomerClick = { navController.navigate(CustomerDetails(it)) },
                navigateToSetting = { navController.navigate(Setting) }
            )
        }
        composable<AddCustomer> { AddCustomerScreen(onBack = { navController.popBackStack() }) }
        composable<CustomerDetails>(
            typeMap = mapOf()
        ) { backStackEntry ->
            CustomerDetailsScreen(
                customerId = backStackEntry.toRoute<CustomerDetails>().customerId,
                onAddTransactionClick = { navController.navigate(AddTransaction(it)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable<AddTransaction> { AddTransactionScreen(onBack = { navController.popBackStack() }) }

        composable<Setting> {
            val currentLanguage = LanguageManager.getCurrentLanguage()
            val currentTheme = ThemeManager.getCurrentTheme()

            SettingScreen(
                currentLanguage = currentLanguage.displayName,
                onLanguageChange = LanguageManager::changeLanguage,
                currentTheme = currentTheme,
                onThemeChange = ThemeManager::changeTheme,
                onBack = { navController.popBackStack() },
                logout = {
                    authViewModel.logout()
                    navController.navigate(Auth) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        dialog<Restore> {
            RestoreBackupScreen {
                navController.navigate(CustomerList) {
                    popUpTo(Auth) { inclusive = true }
                }
            }
        }
    }
}