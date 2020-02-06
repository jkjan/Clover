package com.jun.clover.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FrdRecmdApi {
    @POST("/recommend")
    fun createFrdRecmd (@Body idRecmder : String) : Call<String>
}