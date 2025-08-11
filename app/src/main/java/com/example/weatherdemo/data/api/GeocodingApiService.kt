package com.example.weatherdemo.data.api

import com.example.weatherdemo.data.models.GeocodingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("geo/1.0/direct")
    suspend fun searchCities(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("appid") appId: String
    ): List<GeocodingDto>
}