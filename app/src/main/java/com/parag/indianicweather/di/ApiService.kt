package com.parag.indianicweather.di



import com.parag.indianicweather.datamodels.ForecastResponse

import com.parag.indianicweather.datamodels.WeatherResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Api service
 *
 * @constructor Create empty Api service
 * will be used for API calling
 */
interface ApiService {



    @Headers(ApiInterface.HEADER_CONTENT_TYPE)
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String?,
        @Query("appid") apiKey: String?,
        @Query("units") units: String?
    ): Response<WeatherResponse>

    @Headers(ApiInterface.HEADER_CONTENT_TYPE)
    @GET("forecast")
    suspend fun getForecast(
        @Query("q") cityName: String?,
        @Query("appid") apiKey: String?,
        @Query("units") units: String?
    ): Response<ForecastResponse>


}