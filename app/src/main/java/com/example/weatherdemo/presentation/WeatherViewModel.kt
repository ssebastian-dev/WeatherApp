package com.example.weatherdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdemo.domain.models.City
import com.example.weatherdemo.domain.models.WeatherForecast
import com.example.weatherdemo.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherScreenUiState())
    val uiState: StateFlow<WeatherScreenUiState> = _uiState.asStateFlow()

    private val searchQueryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val lastCity = repository.getLastSearchedCity()
            if (lastCity != null) {
                _uiState.update {
                    it.copy(
                        selectedCity = lastCity,
                        searchText = lastCity.name,
                        citySearchResults = emptyList(),
                        citySearchError = null,
                        isLoadingWeather = true
                    )
                }
                val forecastUiState = repository.getWeatherForecast(lastCity.lat, lastCity.lon)
                _uiState.update {
                    it.copy(
                        weatherForecast = forecastUiState,
                        isLoadingWeather = false,
                        weatherErrorMessage = forecastUiState.errorMessage
                    )
                }
            }
        }
        // Debounce and search
        searchQueryFlow
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isNotBlank()) {
                    searchCities(query)
                } else {
                    _uiState.update { it.copy(citySearchResults = emptyList(), citySearchError = null) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchText = query, citySearchError = null) }
        searchQueryFlow.value = query
    }

    private fun searchCities(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update { it.copy(isSearchingCities = true, citySearchError = null) }
                val cities = repository.searchCities(query, limit = 5)
                _uiState.update {
                    it.copy(citySearchResults = cities, isSearchingCities = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        citySearchResults = emptyList(),
                        isSearchingCities = false,
                        citySearchError = e.localizedMessage ?: "Error searching cities"
                    )
                }
            }
        }
    }

    fun onCitySelected(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    selectedCity = city,
                    searchText = city.name,
                    citySearchResults = emptyList(),
                    citySearchError = null,
                    isLoadingWeather = true,
                    weatherForecast = null,
                    weatherErrorMessage = null
                )
            }

            repository.saveLastSearchedCity(city)

            val forecast = repository.getWeatherForecast(city.lat, city.lon)
            _uiState.update {
                it.copy(
                    weatherForecast = forecast,
                    isLoadingWeather = false,
                    weatherErrorMessage = forecast.errorMessage
                )
            }
        }
    }
}

data class WeatherScreenUiState(
    val searchText: String = "",
    val citySearchResults: List<City> = emptyList(),
    val citySearchError: String? = null,
    val isSearchingCities: Boolean = false,
    val selectedCity: City? = null,
    val weatherForecast: WeatherForecast? = null,
    val isLoadingWeather: Boolean = false,
    val weatherErrorMessage: String? = null
)
