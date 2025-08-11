package com.example.weatherdemo.domain.models

data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String,
    val localNames: Map<String, String> = emptyMap()
) {
    fun displayName(): String =
        listOfNotNull(name, state.takeIf { it.isNotBlank() }, country).joinToString(", ")
}