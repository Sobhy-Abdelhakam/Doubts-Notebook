package com.sobhy.debtnotebook.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.sobhy.debtnotebook.domain.model.AppTheme

object ThemeManager {
    fun getCurrentTheme(): AppTheme {
        return when (AppCompatDelegate.getDefaultNightMode()) {
            MODE_NIGHT_NO -> AppTheme.LIGHT
            MODE_NIGHT_YES -> AppTheme.DARK
            else -> AppTheme.SYSTEM
        }
    }

    fun changeTheme(theme: AppTheme) {
        val mode = when (theme) {
            AppTheme.LIGHT -> MODE_NIGHT_NO
            AppTheme.DARK -> MODE_NIGHT_YES
            AppTheme.SYSTEM -> MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}