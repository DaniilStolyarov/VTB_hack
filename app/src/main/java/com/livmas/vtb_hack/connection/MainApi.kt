package com.livmas.vtb_hack.connection

import retrofit2.Call
<<<<<<< HEAD
import retrofit2.http.POST
import retrofit2.http.Query
=======
import retrofit2.http.Body
import retrofit2.http.POST
>>>>>>> b88d0ba02436c3be6396991c2b0c96e549ebcfed

interface MainApi {

    @POST("/destination")
<<<<<<< HEAD
    fun sendLocation(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("criteria") criteria: String
    ): Call<BankResponse>
=======
    fun sendLocation(@Body request: BankRequest): Call<List<BankResponse>>
>>>>>>> b88d0ba02436c3be6396991c2b0c96e549ebcfed
}