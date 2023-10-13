package com.livmas.vtb_hack.connection

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
    val id: Int
)

