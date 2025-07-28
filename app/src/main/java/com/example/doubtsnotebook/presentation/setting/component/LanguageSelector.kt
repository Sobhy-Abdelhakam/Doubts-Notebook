package com.example.doubtsnotebook.presentation.setting.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.doubtsnotebook.domain.model.AppLanguage

@Composable
fun LanguageSelector(selected: String, onChange: (String) -> Unit) {
    AppLanguage.entries.forEach {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selected == it.displayName,
                onClick = { onChange(it.code) }
            )
            Text(text = it.displayName)
        }
    }
}