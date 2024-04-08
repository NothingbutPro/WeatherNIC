package com.parag.indianicweather.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.parag.indianicweather.onboarding.fragments.CurrentWeatherFragment
import com.parag.indianicweather.onboarding.fragments.ForecastFragment

class WeatherPagerAdapter(fm: FragmentManager,var arguments: Bundle?) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val NUM_PAGES = 2
    private val TAB_TITLES = arrayOf("Current Weather", "5 Days Forecast")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CurrentWeatherFragment(arguments?.getString("City"))
            1 -> ForecastFragment(arguments?.getString("City"))
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        return NUM_PAGES
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }
}