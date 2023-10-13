package com.livmas.vtb_hack.connection

import android.util.Log
import com.livmas.vtb_hack.BuildConfig
import com.livmas.vtb_hack.MainActivity
import com.yandex.mapkit.geometry.Point
<<<<<<< HEAD
import kotlinx.coroutines.CoroutineDispatcher
=======
>>>>>>> b88d0ba02436c3be6396991c2b0c96e549ebcfed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
<<<<<<< HEAD
=======
import java.lang.Exception
>>>>>>> b88d0ba02436c3be6396991c2b0c96e549ebcfed

class HttpClient(private val activity: MainActivity) {
    private val tag = "http"
    private var api: MainApi
    private var handler: ResponseHandler
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    init {
        api = retrofit.create(MainApi::class.java)
        handler = ResponseHandler(activity)
    }

    fun query(point: Point) {
        val call = api.sendLocation(
<<<<<<< HEAD
            point.latitude,
            point.longitude,
            "000000000000000"
        )

        CoroutineScope(Dispatchers.IO).launch{
            val res = call.execute()
            handler.handle(res)

            Log.d(tag, "Response handled")
=======
            BankRequest(
                point.latitude,
                point.longitude,
                "111111111111111"
            )
        )

        CoroutineScope(Dispatchers.IO).launch{
            try {
                val res = call.execute()
                handler.handle(res)
                Log.d(tag, "Response handled")
            }
            catch (e: Exception) {
                Log.d(tag, e.message.toString())
            }

>>>>>>> b88d0ba02436c3be6396991c2b0c96e549ebcfed
        }
    }
}