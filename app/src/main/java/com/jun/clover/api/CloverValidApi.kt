package com.jun.clover.api

import com.jun.clover.dto.CloverValid
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CloverValidApi {
    @POST("/clover/{prcrsId}")
    fun purchaseClover(@Path("prcrsId") id : String) : Call<String>

    @GET("/clover/{prcrsId}")
    fun getPurchasedClover(@Path("prcrsId") id : String) : Call<ArrayList<CloverValid>>
}