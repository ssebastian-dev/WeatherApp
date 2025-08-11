package com.example.weatherdemo.presentation

import android.app.Application
import com.example.weatherdemo.BuildConfig
import com.example.weatherdemo.data.RetrofitProvider
import com.example.weatherdemo.data.repository.WeatherRepositoryImpl
import com.example.weatherdemo.data.util.dataStore
import com.google.gson.Gson
import com.example.weatherdemo.domain.repository.WeatherRepository

class WeatherApplication : Application() {
    val gson: Gson by lazy { Gson() }
    val geocodingApiService by lazy { RetrofitProvider.geocodingApiService }
    val forecastApiService by lazy { RetrofitProvider.forecastApiService }
    val dataStore by lazy { applicationContext.dataStore }
    val apiKey: String = BuildConfig.API_KEY

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            dataStore = dataStore,
            gson = gson,
            apiGeocodingService = geocodingApiService,
            apiForecastService = forecastApiService,
            apiKey = apiKey
        )
    }
}