package com.livmas.vtb_hack.connection

import android.widget.Toast
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.R
import com.livmas.vtb_hack.object_creaters.Marker
import retrofit2.Response

class ResponseHandler(private val activity: MainActivity) {
    private val marker = Marker(activity)
<<<<<<< HEAD
    fun handle(response: Response<BankResponse>) {
=======
    fun handle(response: Response<List<BankResponse>>) {
>>>>>>> b88d0ba02436c3be6396991c2b0c96e549ebcfed
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
<<<<<<< HEAD
                val point = com.yandex.mapkit.geometry.Point(body.latitude, body.longitude)
                marker.putToastMark(point)
=======

                for (i in body) {
                    val point = com.yandex.mapkit.geometry.Point(i.latitude, i.longitude)
                    activity.runOnUiThread {
                        marker.putToastMark(point)
                    }
                }
>>>>>>> b88d0ba02436c3be6396991c2b0c96e549ebcfed
            }
            else -> Toast.makeText(
                    activity,
                    R.string.server_error,
                    Toast.LENGTH_LONG
                ).show()
        }
    }
}