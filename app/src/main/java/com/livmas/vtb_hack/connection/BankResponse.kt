package com.livmas.vtb_hack.connection

<<<<<<< HEAD
import com.google.gson.annotations.SerializedName

data class BankResponse(
    @SerializedName("ADRESS")
    @Transient
    val address: String?,
    @SerializedName("LATITUDE")
    val latitude: Double,
    @SerializedName("LONGITUDE")
    val longitude: Double,
    @SerializedName("CRITERIA")
    val criteria: String,
    @SerializedName("TYPE")
    val type: Boolean,
    @SerializedName("WORKLOAD")
    val workload: Float,
    @SerializedName("ID")
=======
data class BankResponse(
    val address: String?,
    val latitude: Double,
    val longitude: Double,
    val criteria: String,
    val type: Boolean,
    val workload: Float,
>>>>>>> b88d0ba02436c3be6396991c2b0c96e549ebcfed
    val id: Int
)

