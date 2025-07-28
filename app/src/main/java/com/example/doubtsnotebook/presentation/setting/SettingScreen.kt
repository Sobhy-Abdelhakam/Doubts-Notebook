package com.example.doubtsnotebook.presentation.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.doubtsnotebook.R
import com.example.doubtsnotebook.domain.model.AppTheme
import com.example.doubtsnotebook.presentation.setting.component.LanguageSelector
import com.example.doubtsnotebook.presentation.setting.component.ThemeSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    currentLanguage: String,
    onLanguageChange: (String) -> Unit,
    currentTheme: AppTheme,
    onThemeChange: (AppTheme) -> Unit,
    onBack: () -> Unit,
    logout: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.settings))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.appearance), style = MaterialTheme.typography.titleMedium)
            ThemeSelector(currentTheme, onChange = onThemeChange)
            Text(stringResource(R.string.app_language), style = MaterialTheme.typography.titleMedium)
            LanguageSelector(currentLanguage, onChange = onLanguageChange)

            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.data), style = MaterialTheme.typography.titleMedium)
            ListItem(
                headlineContent = {
                    Text(stringResource(R.string.sync_data))
                },
                leadingContent = {
                    Icon(
                        painterResource(R.drawable.cloud_sync),
                        contentDescription = stringResource(R.string.sync_data),
                        modifier = Modifier.size(40.dp)
                    )
                },
                modifier = Modifier.clickable {
                    viewModel.syncData()
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = logout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.logout))
            }
        }
    }
}