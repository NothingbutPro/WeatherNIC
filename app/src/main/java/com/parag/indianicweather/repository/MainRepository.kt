package com.parag.indianicweather.repository

import com.parag.indianicweather.datamodels.ForecastResponse
import com.parag.indianicweather.datamodels.WeatherResponse
import com.parag.indianicweather.di.ApiService
import com.parag.indianicweather.di.DeleteUserModel
import com.parag.indianicweather.utils.AppSession

import com.paragsharma.myfamous.di.Resource

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Main repository
 *
 * @property apiService
 * @property appSession
 * @constructor Create empty Main repository
 */
@Singleton
class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val appSession: AppSession
) {

    suspend fun getWeather( city: String?, apiKey: String?, units: String?): Resource<WeatherResponse> {
        val response: retrofit2.Response<WeatherResponse>
        try {
            response = apiService.getWeather(city , apiKey,units)
            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }
            }
        } catch (e: HttpException) {
            e.printStackTrace()
            return Resource.Error("Something went wrong")
        } catch (e: IOException) {
            return Resource.Error(e.message.toString())
        } catch (e: Exception) {
            return Resource.Error("Something went wrong")
        }

        return Resource.Error(response.message())


    }
    suspend fun getForecast( city: String?, apiKey: String?, units: String?): Resource<ForecastResponse> {
        val response: retrofit2.Response<ForecastResponse>
        try {
            response = apiService.getForecast(city , apiKey,units)
            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }
            }
        } catch (e: HttpException) {
            e.printStackTrace()
            return Resource.Error("Something went wrong")
        } catch (e: IOException) {
            return Resource.Error(e.message.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Something went wrong")
        }

        return Resource.Error(response.message())


    }

}