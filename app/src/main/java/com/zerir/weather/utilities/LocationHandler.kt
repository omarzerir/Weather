package com.zerir.weather.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationHandler(
    private val context: Activity,
    private val onLocationResult: OnLocationResult
) {

    private val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    interface OnLocationResult {
        fun onLocationGet(location: Location)
        fun onLocationIsNotEnabled()
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if (isLocationEnabled()) {
            mFusedLocationClient.lastLocation.addOnCompleteListener(context) { task ->
                val location: Location? = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
                    onLocationResult.onLocationGet(location)
                }
            }
        } else {
            onLocationResult.onLocationIsNotEnabled()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: android.location.LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            onLocationResult.onLocationGet(mLastLocation)
        }
    }

}