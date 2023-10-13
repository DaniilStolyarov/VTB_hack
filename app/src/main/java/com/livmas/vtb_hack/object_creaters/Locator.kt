package com.livmas.vtb_hack.object_creaters

import android.Manifest
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.livmas.vtb_hack.MainActivity
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.LocationStatus

class Locator(private val activity: MainActivity) {
    private val tag = "location"

    init {
        grantLocationPermissions()

        activity.apply {
            val locationLayer = mapkit.createUserLocationLayer(binding.mvMap.mapWindow)
            locationLayer.isVisible = true

            mapkit.createLocationManager().subscribeForLocationUpdates(
                0.0, 0, 0.0, true, FilteringMode.ON,
                object:com.yandex.mapkit.location.LocationListener {
                    override fun onLocationUpdated(p0: com.yandex.mapkit.location.Location) {
                        val loc = p0.position
                        Log.d(tag, "${loc.latitude}; ${loc.longitude}")
                        holder.location = loc
                    }

                    override fun onLocationStatusUpdated(p0: LocationStatus) {
                        Log.d(tag, "Status: ${p0.name}")
                    }
                })
        }

    }

    private fun grantLocationPermissions() {
        if (checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PermissionChecker.PERMISSION_GRANTED &&
            checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PermissionChecker.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
    }
}