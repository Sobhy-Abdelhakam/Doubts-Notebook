package com.example.doubtsnotebook.presentation.config

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.doubtsnotebook.domain.model.AppLanguage

object LanguageManager {
    fun setDefaultLanguageIfUnset(defaultLang: String) {
        if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(defaultLang))
        }
    }

    fun getCurrentLanguage(): AppLanguage {
        val code = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        return AppLanguage.entries.find { it.code == code } ?: AppLanguage.AR
    }

    fun changeLanguage(langCode: String) {
        val locales = LocaleListCompat.forLanguageTags(langCode)
        AppCompatDelegate.setApplicationLocales(locales)
    }
}