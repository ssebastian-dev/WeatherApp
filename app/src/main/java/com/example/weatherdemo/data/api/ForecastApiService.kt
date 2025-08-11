package com.example.weatherdemo.data.api

import com.example.weatherdemo.data.models.ForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApiService {
    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String = "metric"
    ): ForecastDto
}