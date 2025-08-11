package com.example.weatherdemo.domain.models

data class WeatherForecast(
    val currentDaySummary: CurrentDaySummary? = null,
    val hourlyForecast: List<HourlyForecastItem> = emptyList(),
    val dailyForecast: List<DailyForecastItem> = emptyList(),
    val errorMessage: String? = null
)

data class CurrentDaySummary(
    val temp: Int,
    val feelsLike: Int,
    val icon: String
)

data class HourlyForecastItem(
    val time: String,
    val temp: Int,
    val icon: String
)

data class DailyForecastItem(
    val day: String,
    val icon: String,
    val minTemp: Int,
    val maxTemp: Int
)