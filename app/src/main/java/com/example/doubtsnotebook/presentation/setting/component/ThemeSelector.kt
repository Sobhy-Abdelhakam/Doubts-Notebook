package com.example.doubtsnotebook.presentation.setting.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.doubtsnotebook.domain.model.AppTheme

@Composable
fun ThemeSelector(selected: AppTheme, onChange: (AppTheme) -> Unit) {
    AppTheme.entries.forEach {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selected == it,
                onClick = { onChange(it) }
            )
            Text(text = it.displayName, modifier = Modifier.padding(start = 8.dp))
        }
    }
}