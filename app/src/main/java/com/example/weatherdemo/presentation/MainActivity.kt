package com.example.weatherdemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherdemo.ui.theme.WeatherDemoTheme
import androidx.lifecycle.ViewModelProvider
import com.example.weatherdemo.presentation.components.WeatherForecastScreen

class MainActivity : ComponentActivity() {

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Access Application instance to get shared dependencies
        val app = application as WeatherApplication

        val viewModelFactory = WeatherViewModelFactory(app.weatherRepository)

        weatherViewModel = ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]

        setContent {
            val uiState by weatherViewModel.uiState.collectAsState()
            WeatherDemoTheme {
                WeatherForecastScreen(
                    uiState.searchText,
                    uiState.citySearchResults,
                    citySearchError = uiState.citySearchError,
                    isSearching = uiState.isSearchingCities,
                    weatherForecast = uiState.weatherForecast,
                    isLoadingWeather = uiState.isLoadingWeather,
                    weatherErrorMessage = uiState.weatherErrorMessage,
                    onSearchTextChange = { query ->
                        weatherViewModel.onSearchQueryChanged(query) },
                    onCitySelected = { city ->
                        weatherViewModel.onCitySelected(city) }
                )
            }
        }
    }
}