package com.livmas.vtb_hack.connection

import android.util.Log
import com.livmas.vtb_hack.BuildConfig
import com.livmas.vtb_hack.MainActivity
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            BankRequest(
                point.latitude,
                point.longitude,
                "111000000000000"
            )
        )

        CoroutineScope(Dispatchers.IO).launch{
            val res = call.execute()
            handler.handle(res)

            Log.d(tag, "Response handled")
        }
    }
}