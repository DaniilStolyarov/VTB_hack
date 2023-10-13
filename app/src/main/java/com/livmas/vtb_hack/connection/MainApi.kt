package com.livmas.vtb_hack.connection

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {

    @POST("/destination")
    fun sendLocation(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("criteria") criteria: String
    ): Call<BankResponse>
}