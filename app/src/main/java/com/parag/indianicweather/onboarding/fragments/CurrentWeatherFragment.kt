package com.parag.indianicweather.onboarding.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.parag.indianicweather.BaseFragment
import com.parag.indianicweather.databinding.FragmentCurrentWeatherBinding
import com.parag.indianicweather.datamodels.WeatherResponse
//import com.parag.indianicweather.databinding.FragmentOtpVerificationBinding
import com.parag.indianicweather.onboarding.WelcomeActivity
import com.parag.indianicweather.onboarding.viewmodels.OnBoardingViewModel
import com.parag.indianicweather.utils.API_KEY
import com.paragsharma.myfamous.di.Resource

class CurrentWeatherFragment(var city: String?) : BaseFragment() {
    private var _binding : FragmentCurrentWeatherBinding?=null
    private lateinit var weatherViewModel: OnBoardingViewModel
    private val binding get()  =_binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_current_weather, container, false)
        _binding = FragmentCurrentWeatherBinding.inflate(layoutInflater)
        initializeNavigation(requireActivity())
        weatherViewModel = (activity as WelcomeActivity).getWeatherViewModel()
        initUI()
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Observe ViewModel data changes
        weatherViewModel.generateWeatherResponse.observe(viewLifecycleOwner, Observer { it ->
            // Update UI with weather data
            when (it) {
                is Resource.Success -> {

                    Log.e("weatherViewModel" ,"generateWeatherResponse load Data "+it.data)
                    updateUI(it.data);
                }

                is Resource.Error -> {


                    it.message?.let { message ->
                        try{
                            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                            Log.e(javaClass.name, "Error: $message")
                        }catch (E :Exception)
                        {
                            E.printStackTrace()
                        }

                    }
                }

                is Resource.Loading -> {

                }

                else -> {
                    Log.e("weatherViewModel" ,"Failed to load Data")
                }
            }


//
        })

    }

    private fun initUI() {
        weatherViewModel.getWeather(city , API_KEY ,"metric")

//        weatherViewModel.getForecast("Indore" , API_KEY ,"metric")
    }

    private fun updateUI(data: WeatherResponse?) {
        binding!!.tvTemperatureTextView.text = "Temperature: ${data!!.getMain()!!.temp}°C"
        binding!!.tvFeelsLikeTextView.text = "Feels Like: ${data!!.getMain()!!.feelsLike}°C"
        binding!!.tvFeelsLikeTextView.text = "Humidity: ${data!!.getMain()!!.humidity}%"
        binding!!.tvDescription.text = "Weather: ${data!!.getWeather()!!.get(0).description}"

    }
}