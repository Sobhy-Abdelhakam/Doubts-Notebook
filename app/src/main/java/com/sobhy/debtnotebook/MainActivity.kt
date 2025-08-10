package com.sobhy.debtnotebook

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.sobhy.debtnotebook.presentation.config.LanguageManager
import com.sobhy.debtnotebook.presentation.navigation.AppNavHost
import com.sobhy.debtnotebook.ui.theme.DoubtsNotebookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set Arabic is default language
        LanguageManager.setDefaultLanguageIfUnset("ar")
        enableEdgeToEdge()
        setContent {
            DoubtsNotebookTheme {
                AppNavHost()
            }
        }
    }
}