package com.livmas.vtb_hack.connection

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {

    @POST("/")
    fun senLocation(@Query("latitude") latitude: Float,
             @Query("longitude") longitude: Float): Call<Response>
}