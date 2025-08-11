package com.example.weatherdemo.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun WeatherIcon(iconCode: String, modifier: Modifier = Modifier) {
    val url = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
    // Load image via Coil
    AsyncImage(
        model = url,
        contentDescription = "Weather Icon",
        modifier = modifier
    )
}