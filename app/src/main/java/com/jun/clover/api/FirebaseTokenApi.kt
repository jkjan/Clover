package com.jun.clover.api

import com.jun.clover.dto.FirebaseToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FirebaseTokenApi {
    @POST("/clover/firebase/token")
    fun addToken(@Body firebaseToken : String) : Call<FirebaseToken>
}