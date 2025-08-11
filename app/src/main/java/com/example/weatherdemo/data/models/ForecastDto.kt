package com.example.weatherdemo.data.models

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastEntry>,
    val city: CityInfo
)

data class ForecastEntry(
    val dt: Long,                          // Unix timestamp
    val main: MainData,
    val weather: List<WeatherData>,       // List containing weather descriptions/icons
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain? = null,
    val snow: Snow? = null,
    val sys: Sys,
    @SerializedName("dt_txt")
    val dtText: String                    // Date-time text, e.g., "2023-07-08 12:00:00"
)

data class MainData(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    @SerializedName("grnd_level")
    val grndLevel: Int,
    val humidity: Int,
    @SerializedName("temp_kf")
    val tempKf: Double
)

data class WeatherData(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Clouds(
    val all: Int
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double? = null
)

data class Rain(
    @SerializedName("3h")
    val volumeLast3h: Double
)

data class Snow(
    @SerializedName("3h")
    val volumeLast3h: Double
)

data class Sys(
    val pod: String
)

data class CityInfo(
    val id: Long,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int?,
    val timezone: Int?,
    val sunrise: Long?,
    val sunset: Long?
)

data class Coord(
    val lat: Double,
    val lon: Double
)