package com.example.weatherdemo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun WeatherDemoTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        typography = Typography,
        content = content,
    )
}