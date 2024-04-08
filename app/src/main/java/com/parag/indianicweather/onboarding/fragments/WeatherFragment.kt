package com.parag.indianicweather.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parag.indianicweather.BaseFragment
import com.parag.indianicweather.databinding.FragmentWeatherBinding

import com.parag.indianicweather.datamodels.WeatherResponse
import com.parag.indianicweather.onboarding.WeatherPagerAdapter
import com.parag.indianicweather.onboarding.viewmodels.OnBoardingViewModel
import com.parag.indianicweather.utils.API_KEY
import com.parag.indianicweather.utils.AppSession

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : BaseFragment() ,View.OnClickListener {
//    private lateinit var viewPager: ViewPager
//    private lateinit var tabLayout: TabLayout

    private var bundle: Bundle?=null
    private var _binding : FragmentWeatherBinding?=null
    private lateinit var weatherViewModel: OnBoardingViewModel
    private val binding get()  =_binding
    @Inject
    lateinit var appSession: AppSession
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding =FragmentWeatherBinding.inflate(layoutInflater)
        initializeNavigation(requireActivity())
        val adapter = WeatherPagerAdapter(childFragmentManager ,arguments)
        binding!!. viewPager.adapter = adapter
        binding!!.tabLayout.setupWithViewPager(_binding!!.viewPager)

        return _binding!!.root
    }

    private fun initUI() {

        weatherViewModel.getWeather("Indore" , API_KEY ,"metric")
//        weatherViewModel.getForecast("Indore" , API_KEY ,"metric")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun updateUI(data: WeatherResponse?) {

    }


    override fun onClick(p0: View?) {
        when(p0?.id)
        {

//            R.id.rl_back -> requireActivity().onBackPressed()
        }
    }



}