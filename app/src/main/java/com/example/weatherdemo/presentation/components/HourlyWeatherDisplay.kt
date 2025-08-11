package com.example.weatherdemo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherdemo.domain.models.HourlyForecastItem

@Composable
fun HourlyWeatherDisplay(
    item: HourlyForecastItem,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
//    val formattedTime = remember(weatherData) {
//        weatherData.time.format(
//            DateTimeFormatter.ofPattern("HH:mm")
//        )
//    }
//    Column(
//        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = formattedTime,
//            color = Color.LightGray
//        )
//        Image(
//            painter = painterResource(id = weatherData.weatherType.iconRes),
//            contentDescription = null,
//            modifier = Modifier.width(40.dp)
//        )
//        Text(
//            text = "${weatherData.temperatureCelsius}°C",
//            color = textColor,
//            fontWeight = FontWeight.Bold
//        )
//    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.time,
            color = textColor,
            style = MaterialTheme.typography.bodySmall
        )
        WeatherIcon(
            iconCode = item.icon,
            modifier = Modifier.size(36.dp)
        )
        Text(
            text = "${item.temp}°C",
            color = textColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }



}