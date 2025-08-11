package com.example.weatherdemo.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherdemo.data.models.ForecastDto
import com.example.weatherdemo.data.models.ForecastEntry
import com.example.weatherdemo.domain.models.CurrentDaySummary
import com.example.weatherdemo.domain.models.DailyForecastItem
import com.example.weatherdemo.domain.models.HourlyForecastItem
import com.example.weatherdemo.domain.models.WeatherForecast
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
fun ForecastDto.toDomainModel(): WeatherForecast {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dayFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH) // MON, TUE, etc.

    val groupedByDay = list.groupBy {
        LocalDateTime.parse(it.dtText, dateFormatter).toLocalDate()
    }

    val today = groupedByDay.keys.minOrNull() ?: LocalDate.now()
    val todayList = groupedByDay[today] ?: emptyList()

    val currentDaySummary = todayList.firstOrNull()?.let {
        CurrentDaySummary(
            temp = it.main.temp.roundToInt(),
            feelsLike = it.main.feelsLike.roundToInt(),
            icon = it.weather.firstOrNull()?.icon ?: "01d"
        )
    }

    val hourlyForecast = todayList.map {
        HourlyForecastItem(
            time = LocalDateTime.parse(it.dtText, dateFormatter).toLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm")),
            temp = it.main.temp.roundToInt(),
            icon = it.weather.firstOrNull()?.icon ?: "01d"
        )
    }

    val dailyForecast = groupedByDay.filterKeys { it != today }.map { (date, entries) ->
        val dayName = date.format(dayFormatter).uppercase()
        val minTemp = entries.minOfOrNull { it.main.tempMin } ?: 0.0
        val maxTemp = entries.maxOfOrNull { it.main.tempMax } ?: 0.0

        val majorityIcon = entries
            .flatMap { it.weather }
            .groupingBy { it.icon }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key ?: "01d"

        DailyForecastItem(
            day = dayName,
            icon = majorityIcon,
            minTemp = minTemp.roundToInt(),
            maxTemp = maxTemp.roundToInt()
        )
    }

    return WeatherForecast(
        currentDaySummary = currentDaySummary,
        hourlyForecast = hourlyForecast,
        dailyForecast = dailyForecast
    )
}