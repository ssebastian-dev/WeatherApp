package com.example.weatherdemo.domain.repository

import com.example.weatherdemo.domain.models.City
import com.example.weatherdemo.domain.models.WeatherForecast

interface WeatherRepository {
    suspend fun searchCities(query: String, limit: Int = 5): List<City>
    suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherForecast
    suspend fun saveLastSearchedCity(city: City)
    suspend fun getLastSearchedCity(): City?
}