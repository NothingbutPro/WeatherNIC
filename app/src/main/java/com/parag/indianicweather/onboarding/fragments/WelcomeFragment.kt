package com.parag.indianicweather.onboarding.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parag.indianicweather.BaseFragment
import com.parag.indianicweather.R
import com.parag.indianicweather.databinding.FragmentWelcomeBinding
import com.parag.indianicweather.onboarding.WelcomeActivity
import com.parag.indianicweather.utils.GPSLocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class WelcomeFragment : BaseFragment() ,View.OnClickListener , OnMapReadyCallback {
    private var city: String = ""
    var _binding: FragmentWelcomeBinding? = null
    private var isGPS: Boolean = false
    private val binding get() = _binding
    private lateinit var mMap: GoogleMap
//    private var circle: Circle? = null
    var latLong: LatLng? = null
    val gpsLocationViewModel: GPSLocationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBinding.inflate(layoutInflater)
        initializeNavigation(requireActivity())

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
//        val mapFragment = childFragmentManager.findFragmentById(R.id.fr_small_map) as SupportMapFragment?
//        MapsInitializer.initialize(
//            requireActivity(), MapsInitializer.Renderer.LATEST
//        ) {
//
//        }
//
//        mapFragment!!.getMapAsync(this)

        return  _binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        gpsLocationViewModel.locationAuthentication.observe(requireActivity()) {
//            val addresses: List<Address>?
//            val geocoder: Geocoder = Geocoder(requireActivity(), Locale.getDefault())
//            addresses = geocoder.getFromLocation(
//                it.latitude,
//                it.longitude,
//                1
//            )
//
//
//        }

        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding!!.mapSelection.onCreate(savedInstanceState)
        binding!!.mapSelection.getMapAsync(this)
        binding?.ivMylocimage?.setOnClickListener(this)
        binding?.btnConfirmLocation?.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.iv_mylocimage -> {
                getLocationWithImage()
            }

            R.id.btn_confirm_location -> {
                if (latLong == null) {
                    Toast.makeText(
                        requireActivity(),
                        "Please select a Location",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val targetLocation = Location("")

                targetLocation.latitude = latLong?.latitude!!

                targetLocation.longitude = latLong?.longitude!!
                val bundle = Bundle()
                bundle.putString("City" ,city)
                navigateToScreen(R.id.nav_fragment_otp_verification , bundle)

            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        (requireActivity() as WelcomeActivity).gpsLocationViewModel.locationAuthentication.observe(requireActivity()) {
            latLong = LatLng(it.latitude, it.longitude)
            initializeMap(googleMap, it.latitude, it.longitude)


        }
    }

    /**
     * Initialize_map
     *
     * @param map
     */
    private fun initializeMap(map: GoogleMap, lat: Double, long: Double) {
        if (isAdded) {
            mMap = map
            // Add markers, polylines, etc. to the map
            val sydney = LatLng(lat, long)
            val marker = MarkerOptions().position(sydney).title("My Location")
            mMap.addMarker(marker.apply {
                icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.ic_red_marker))
            })


            // Create a CameraUpdate object to specify the zoom level and target position
            val cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(
                marker.position,
                15f
            ) // 15f is the desired zoom level
            // Animate the camera to the marker position
            mMap.animateCamera(cameraUpdate)
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            // Disable the built-in zoom controls
            mMap.uiSettings.isZoomControlsEnabled = false
            mMap.clear()
            mMap.setOnCameraMoveStartedListener {
                Log.e("camMove", "setOnCameraMoveStartedListener")
//                circle?.remove()
//                circle = null
                getLocationWithImage()

            }
            val addresses: List<Address>?
            val geocoder: Geocoder = Geocoder(requireActivity(), Locale.getDefault())
            addresses = geocoder.getFromLocation(
                lat,
                long,
                1
            )
            if (addresses?.get(0) != null)
            {
                binding!!.tvAddressAt.text = addresses[0].getAddressLine(0).toString()
                city = addresses[0].locality
                Log.e("City" , ""+city);
            }

        }
        gpsLocationViewModel.locationAuthentication.observe(
            requireActivity()
        ) {
            latLong = LatLng(it.latitude, it.longitude)
            initializeMap(mMap, it.latitude, it.longitude)
        }

    }

    private fun getLocationWithImage() {

        val points = mMap.projection.visibleRegion.latLngBounds.center;
        val marker = MarkerOptions().position(points).title("My Location")
        mMap.addMarker(marker.apply {
            icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.ic_red_marker))
        })
        // Create a CameraUpdate object to specify the zoom level and target position
        val cameraUpdate: CameraUpdate =
            CameraUpdateFactory.newLatLngZoom(marker.position, 15f) // 15f is the desired zoom level
        // Animate the camera to the marker position
        mMap.animateCamera(cameraUpdate)

//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        // Disable the built-in zoom controls
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.clear()


        latLong = LatLng(points?.latitude!!, points?.longitude!!)
//        DataManger.mylocations.add(targetLocation)

        var addresses: List<Address>? = null
        val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
        Log.e("latitude", "" + latLong?.latitude)
        Log.e("longitude", "" + latLong?.longitude)
        try {
            addresses = geocoder.getFromLocation(
                points.latitude,
                points.longitude,
                1
            )
//            addOrRemoveCircle(0, points)

            if (addresses?.get(0) != null)
            {
                binding!!.tvAddressAt.text = addresses[0].getAddressLine(0).toString()
                 city = addresses[0].locality
                Log.e("City" , ""+city);
            }
            val addressLine = addresses!![0].getAddressLine(0)





        } catch (E: Exception) {
            E.printStackTrace()
        }

    }
    /**
     * Bitmap descriptor from vector
     *
     * @param context
     * @param vectorDrawableResourceId
     * @return
     */
    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_red_marker)
        background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorDrawableResourceId)
//        vectorDrawable!!.setBounds(40, 20, vectorDrawable.intrinsicWidth + 40, vectorDrawable!!.intrinsicHeight + 20
//        )
        val bitmap = Bitmap.createBitmap(
            background.intrinsicWidth, background.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        background.draw(canvas)
        vectorDrawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun addOrRemoveCircle(type: Int, points: LatLng?) {

        // Add a circle to the map to represent the radius
        val circleOptions = CircleOptions()
            .center(points!!)
            .radius(200.0) // Set the radius in meters
            .strokeWidth(2f)
            .strokeColor(Color.BLUE)
            .fillColor(Color.parseColor("#200000FF")) // Semi-transparent blue fill
//        circle = mMap.addCircle(circleOptions)


    }
    private fun placeMarker(sydney: LatLng, googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        mMap.addMarker(
            MarkerOptions().position(sydney).title("My Location")
        )
        //Move the camera to the user's location and zoom in!
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                sydney, 12.0f
            )
        )
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    /**
     * On resume
     *
     */
    override fun onResume() {
        super.onResume()
        binding!!.mapSelection.onResume()

    }

    /**
     * On pause
     *
     */
    override fun onPause() {
        super.onPause()
        binding!!.mapSelection.onPause()
    }

    /**
     * On destroy
     *
     */
    override fun onDestroy() {
        _binding = null
        try {
            binding!!.mapSelection.onDestroy()
        } catch (E: Exception) {
            E.printStackTrace()
        }

        super.onDestroy()
    }


    /**
     * On low memory
     *
     */
    override fun onLowMemory() {
        super.onLowMemory()
        binding!!.mapSelection.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

}