package com.linkon.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.linkon.R
import com.linkon.base.BaseFragment
import com.linkon.databinding.FragmentTrackingLiveBinding
import com.linkon.ui.orders.OrdersViewModel
import com.linkon.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TrackLiveFragment :
    BaseFragment<FragmentTrackingLiveBinding>(), OnMapReadyCallback {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentTrackingLiveBinding {
        return FragmentTrackingLiveBinding.inflate(inflater, container, false)
    }

    private val ordersViewModel by viewModels<OrdersViewModel>()
    private val args: TrackLiveFragmentArgs by navArgs()
    private lateinit var orderNo: String

    private lateinit var mMap: GoogleMap
    private var pendingMapAction: ((GoogleMap) -> Unit)? = null

    // Replace with your start and end locations
    private var startLatLng = LatLng(10.7769, 106.7009)
    private val endLatLng = LatLng(10.77303635721695, 106.72590820488634)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        //orderNo = args.orderNo
        //Timber.d(">>>OrderNo: $orderNo")
        ordersViewModel.getOrderTrajectory(orderNo)
        ordersViewModel.uiGetOrderTrajectoryModel.collectWhenStarted { uiState ->
            val isLoading = uiState.isLoading
            binding.loadingProgress.isVisible = isLoading

            if(!isLoading){
                val orderTrajectory = uiState.data
                if(orderTrajectory != null){
                    val data = orderTrajectory.data
                   Timber.d(">>>>uiGetOrderTrajectoryModel: ${data?.size}")

                }else{
                    Timber.d(">>>>uiGetOrderTrajectoryModel: No data")
                }
            }else{
                Timber.d(">>>>uiGetOrderTrajectoryModel: Loading...")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderNo = args.orderNo
        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Call the function to check permissions and get location
        getCurrentLocation()
    }

    private fun initViews() {
        binding.apply {
            toolbarTrackingLive.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        val mapFragment = binding.mapFragment
        mapFragment.getFragment<SupportMapFragment>().getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        showToast(requireContext(), "Map is ready")
        pendingMapAction?.let { action ->
            action(mMap)
            pendingMapAction = null
        }

    }

    private fun addMarkers() {
        val iconStart = BitmapDescriptorFactory.fromResource(R.drawable.ic_orange_car_38)
        if (::mMap.isInitialized) {
            mMap.addMarker(MarkerOptions()
                .position(startLatLng)
                .icon(iconStart))
            mMap.addMarker(MarkerOptions().position(endLatLng))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 16f))
        }else {
            // Map is not ready, save the action to be run later in onMapReady
            showToast(requireContext(), "Map not ready, queueing action...")
            pendingMapAction = { map ->
                map.addMarker(MarkerOptions()
                    .position(startLatLng)
                    .icon(iconStart))
                map.addMarker(MarkerOptions().position(endLatLng))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 16f))
            }
        }
    }

    /**
     * Location
     */
    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog.
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                getCurrentLocation()
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                getCurrentLocation()
            }

            else -> {
                // No location access granted.
                showToast(requireContext(), "Location permission denied")

            }
        }
    }
    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted, get the location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        Timber.d(">>>Lat: $latitude, Lon: $longitude")
                        startLatLng = LatLng(latitude, longitude)
                        addMarkers()
                    } else {
                        Timber.d(">>>Location not found. Try again.")
                    }
                }
                .addOnFailureListener {
                    Timber.d(">>>Failed to get location.")
                }
        } else {
            // Permission is not granted, request it
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

}