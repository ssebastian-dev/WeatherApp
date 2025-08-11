package com.example.weatherdemo.data.models

import com.google.gson.annotations.SerializedName

data class GeocodingDto(
    val name: String,
    @SerializedName("local_names")
    val localNames: Map<String, String>?,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)