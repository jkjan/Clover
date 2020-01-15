package com.jun.clover.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface CloverValidApi {
    @POST("/clover/{id}")
    fun purchaseClover(@Path("id") id : String) : Call<String>
}