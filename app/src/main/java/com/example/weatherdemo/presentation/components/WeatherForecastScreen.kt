package com.example.weatherdemo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherdemo.domain.models.City
import com.example.weatherdemo.domain.models.WeatherForecast
import com.example.weatherdemo.ui.theme.Error
import com.example.weatherdemo.ui.theme.Gray
import com.example.weatherdemo.ui.theme.Gray2
import com.example.weatherdemo.ui.theme.NeutralVariant10
import com.example.weatherdemo.ui.theme.NeutralVariant30

@Composable
fun WeatherForecastScreen(
    searchText: String,
    citySearchResults: List<City>,
    citySearchError: String?,
    isSearching: Boolean,
    weatherForecast: WeatherForecast?,
    isLoadingWeather: Boolean,
    weatherErrorMessage: String?,
    onSearchTextChange: (String) -> Unit,
    onCitySelected: (City) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralVariant10)
            .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {

        SearchBar(
            searchText = searchText,
            onSearchTextChange = onSearchTextChange,
            citySearchResults = citySearchResults,
            citySearchError = citySearchError,
            isSearching = isSearching,
            onCitySelected = onCitySelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Show loading indicator during forecast fetch
        if (isLoadingWeather) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NeutralVariant10),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        // Show error message if weather fetch failed
        else if (!weatherErrorMessage.isNullOrEmpty()) {
            Text(
                text = weatherErrorMessage,
                color = Error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        // Show weather forecast data when available
        else {
            weatherForecast?.let { forecast ->

                Column(modifier = Modifier.padding(16.dp)) {

                    forecast.currentDaySummary?.let { summary ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = NeutralVariant30
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                WeatherIcon(
                                    iconCode = summary.icon,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text(
                                        text = "${summary.temp}°C",
                                        color = Color.White ,
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                    Text(
                                        text = "Feels like ${summary.feelsLike}°C",
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    WeatherForecastCard(
                        title = "Hourly Forecast",
                        isHorizontal = true,
                        items = forecast.hourlyForecast,
                        content = { item ->
                            HourlyWeatherDisplay(
                                item = item
                            )
                        }
                    )

                    Spacer(Modifier.height(24.dp))

                    WeatherForecastCard(
                        title = "Daily Forecast",
                        isHorizontal = false,
                        items = forecast.dailyForecast,
                        content = { item ->
                            DailyWeatherDisplay(
                                item = item
                            )
                        }
                    )
                }
            } ?: run {
                // If no forecast yet and not loading, show placeholder or prompt
                Text(
                    text = "Search for a city to view the forecast.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}