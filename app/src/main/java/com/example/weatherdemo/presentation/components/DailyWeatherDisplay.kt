package com.example.weatherdemo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.weatherdemo.domain.models.DailyForecastItem

@Composable
fun DailyWeatherDisplay(
    item: DailyForecastItem,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
//    val formattedTime = remember(weatherData) {
//        weatherData.time.format(
//            DateTimeFormatter.ofPattern("E")
//        )
//    }
//    Row(
//        modifier = modifier,
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
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
//            text = "Min ${weatherData.temperatureCelsius}°C - Max ${weatherData.temperatureCelsius}°C",
//            color = textColor,
//            fontWeight = FontWeight.Bold
//        )
//    }


    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.day,
            color = textColor,
            modifier = Modifier.width(40.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.width(16.dp))
        WeatherIcon(
            iconCode = item.icon,
            modifier = Modifier.size(36.dp)
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = "Min ${item.minTemp}°C - Max ${item.maxTemp}°C",
            color = textColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }

}