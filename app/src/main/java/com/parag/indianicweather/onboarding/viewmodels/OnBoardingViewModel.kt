package com.parag.indianicweather.onboarding.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parag.indianicweather.datamodels.ForecastResponse
import com.parag.indianicweather.datamodels.WeatherResponse
import com.parag.indianicweather.repository.MainRepository
import com.paragsharma.myfamous.di.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(val repository: MainRepository) :ViewModel() {
     val generateWeatherResponse = MutableLiveData<Resource<WeatherResponse>>()
     val generateWeatherForCastResponse = MutableLiveData<Resource<ForecastResponse>>()


    fun getWeather(city: String?,
                   apiKey: String?,
                   units: String?)
    {
        generateWeatherResponse.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.Main).launch {
            val resource = repository.getWeather(city ,apiKey,units)
            generateWeatherResponse.postValue(resource)
        }

    }

    fun getForecast(city: String?,
                   apiKey: String?,
                   units: String?)
    {
        generateWeatherForCastResponse.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.Main).launch {
            val resource = repository.getForecast(city ,apiKey,units)
            generateWeatherForCastResponse.postValue(resource)
        }

    }




    fun clearData(){
        onCleared()
    }
    override fun onCleared() {
        super.onCleared()
        Log.e("OnBoardingViewModel" , "onCleared")
    }

}