package com.example.doubtsnotebook

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.doubtsnotebook.domain.model.AppLanguage
import com.example.doubtsnotebook.domain.model.AppTheme
import com.example.doubtsnotebook.presentation.auth.AuthScreen
import com.example.doubtsnotebook.presentation.auth.AuthViewModel
import com.example.doubtsnotebook.presentation.customers.addcustomer.AddCustomerScreen
import com.example.doubtsnotebook.presentation.customers.addtransaction.AddTransactionScreen
import com.example.doubtsnotebook.presentation.customers.customerList.CustomerListScreen
import com.example.doubtsnotebook.presentation.customers.customerdetails.CustomerDetailsScreen
import com.example.doubtsnotebook.presentation.restorebackup.RestoreBackupScreen
import com.example.doubtsnotebook.presentation.setting.SettingScreen
import com.example.doubtsnotebook.ui.theme.DoubtsNotebookTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set Arabic is default language
        if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
        }
        enableEdgeToEdge()
        setContent {
            DoubtsNotebookTheme {
                val navController = rememberNavController()
                val viewModel: AuthViewModel = hiltViewModel()
                val startDestination =
                    if (viewModel.getCurrentUserId() != null) CustomerList else Auth
                NavHost(navController = navController, startDestination = startDestination) {
                    composable<Auth> {
                        AuthScreen(
                            viewModel,
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
                        val currentLanguageCode =
                            AppCompatDelegate.getApplicationLocales().toLanguageTags()
                        val currentLanguage =
                            AppLanguage.entries.find { it.code == currentLanguageCode }
                                ?: AppLanguage.AR

                        val currentTheme = when (AppCompatDelegate.getDefaultNightMode()) {
                            MODE_NIGHT_NO -> AppTheme.LIGHT
                            MODE_NIGHT_YES -> AppTheme.DARK
                            else -> AppTheme.SYSTEM
                        }
                        SettingScreen(
                            currentLanguage = currentLanguage.displayName,
                            onLanguageChange = { langCode ->
                                val locales = LocaleListCompat.forLanguageTags(langCode)
                                AppCompatDelegate.setApplicationLocales(locales)
                            },
                            currentTheme = currentTheme,
                            onThemeChange = { theme ->
                                val themeCode = when (theme) {
                                    AppTheme.LIGHT -> MODE_NIGHT_NO
                                    AppTheme.DARK -> MODE_NIGHT_YES
                                    AppTheme.SYSTEM -> MODE_NIGHT_FOLLOW_SYSTEM
                                }
                                AppCompatDelegate.setDefaultNightMode(themeCode)
                            },
                            onBack = { navController.popBackStack() },
                            logout = {
                                viewModel.logout()
                                navController.navigate(Auth) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }
                    composable<Restore> {
                        RestoreBackupScreen {
                            navController.navigate(CustomerList) {
                                popUpTo(Auth) {
                                    inclusive = true
                                }
                            }
                        }
                    }
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

@Serializable
object Auth

@Serializable
object Setting

@Serializable
object Restore