package com.parag.indianicweather.onboarding.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.parag.indianicweather.BaseFragment
import com.parag.indianicweather.databinding.FragmentForcastBinding
import com.parag.indianicweather.datamodels.ForecastResponse
import com.parag.indianicweather.onboarding.ForCastItemAdapter
import com.parag.indianicweather.onboarding.WelcomeActivity
import com.parag.indianicweather.onboarding.viewmodels.OnBoardingViewModel
import com.parag.indianicweather.utils.API_KEY
import com.paragsharma.myfamous.di.Resource

class ForecastFragment(var city: String?) : BaseFragment() {
    private var _binding : FragmentForcastBinding?=null
    private lateinit var weatherViewModel: OnBoardingViewModel
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForcastBinding.inflate(layoutInflater)
        initializeNavigation(requireActivity())
        weatherViewModel = (activity as WelcomeActivity).getWeatherViewModel()
        initUI()
        return  _binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Observe ViewModel data changes
        weatherViewModel.generateWeatherForCastResponse.observe(viewLifecycleOwner, Observer { it ->
            // Update UI with weather data
            when (it) {
                is Resource.Success -> {

                    Log.e("weatherViewModel" ,"generateWeatherForCastResponse  load Data "+it.data)
                    updateUI(it.data)
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


//            updateUI(weatherData)
        })

    }

    private fun initUI() {
        weatherViewModel.getForecast(city , API_KEY ,"metric")
        binding?.recForcast?.layoutManager =LinearLayoutManager(requireActivity())
    }

    private fun updateUI(data: ForecastResponse?) {

        binding?.recForcast?.adapter =  ForCastItemAdapter(requireActivity() ,data?.list,city)

    }
}