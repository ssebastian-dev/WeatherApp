package com.example.weatherdemo.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.weatherdemo.data.api.ForecastApiService
import com.example.weatherdemo.data.api.GeocodingApiService
import com.example.weatherdemo.data.mapper.toDomainModel
import com.example.weatherdemo.domain.models.City
import com.example.weatherdemo.domain.models.WeatherForecast
import com.example.weatherdemo.domain.repository.WeatherRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

class WeatherRepositoryImpl(
    private val apiGeocodingService: GeocodingApiService,
    private val apiForecastService: ForecastApiService,
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson,
    private val apiKey: String
) : WeatherRepository {

    private val LAST_CITY_KEY = stringPreferencesKey("last_searched_city")

    override suspend fun searchCities(query: String, limit: Int): List<City> {
        return try {
            apiGeocodingService.searchCities(query, limit, apiKey).map { dto ->
                City(
                    name = dto.name,
                    lat = dto.lat,
                    lon = dto.lon,
                    country = dto.country,
                    state = dto.state ?: "",
                    localNames = dto.localNames ?: emptyMap()
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherForecast {
        return try {
            val response = apiForecastService.getForecast(lat, lon, apiKey)
            response.toDomainModel()
        } catch (e: Exception) {
            WeatherForecast(
                errorMessage = e.message
            ) // error model
        }
    }

    override suspend fun saveLastSearchedCity(city: City) {
        val cityJson = gson.toJson(city)
        dataStore.edit { prefs: MutablePreferences ->
            prefs[LAST_CITY_KEY] = cityJson
        }
    }

    override suspend fun getLastSearchedCity(): City? {
        val prefs: Preferences = dataStore.data.first()
        return prefs[LAST_CITY_KEY]?.let { json ->
            try {
                gson.fromJson(json, City::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }
}