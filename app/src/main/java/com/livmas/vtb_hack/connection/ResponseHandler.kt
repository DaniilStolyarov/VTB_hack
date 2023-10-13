package com.livmas.vtb_hack.connection

import android.widget.Toast
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.R
import com.livmas.vtb_hack.object_creaters.Marker
import retrofit2.Response

class ResponseHandler(private val activity: MainActivity) {
    private val marker = Marker(activity)
    fun handle(response: Response<BankResponse>) {
        val body = response.body()
        if (body == null) {
            Toast.makeText(
                activity,
                R.string.server_error,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        when (response.code()) {
            200 -> {
                val point = com.yandex.mapkit.geometry.Point(body.latitude, body.longitude)
                marker.putToastMark(point)
            }
            else -> Toast.makeText(
                    activity,
                    R.string.server_error,
                    Toast.LENGTH_LONG
                ).show()
        }
    }
}