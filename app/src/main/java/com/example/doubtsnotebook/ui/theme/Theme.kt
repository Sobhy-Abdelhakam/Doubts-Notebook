package com.example.doubtsnotebook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = warmOliveGreen,
    secondary = beigeSugarGolden,
    tertiary = darkGrayBrown
)

private val LightColorScheme = lightColorScheme(
    primary = warmOliveGreen,
    secondary = sepia,
    tertiary = earthyGold
)

@Composable
fun DoubtsNotebookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}