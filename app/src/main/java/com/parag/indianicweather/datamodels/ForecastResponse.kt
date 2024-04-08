package com.parag.indianicweather.datamodels

// ForecastResponse.kt
data class ForecastResponse(
    val list: List<ForecastData>
)

data class ForecastData(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Weather(
    val description: String
)