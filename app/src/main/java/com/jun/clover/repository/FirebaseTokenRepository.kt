package com.jun.clover.repository

import android.util.Log
import com.jun.clover.api.FirebaseTokenApi
import com.jun.clover.dto.FirebaseToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirebaseTokenRepository (private val firebaseTokenApi: FirebaseTokenApi) {
    fun addToken(token : String) {
        firebaseTokenApi.addToken(token).enqueue(object : Callback<FirebaseToken> {
            override fun onResponse(call: Call<FirebaseToken>, response: Response<FirebaseToken>) {
                if (response.body() != null) {
                    Log.d("add token ok", response.body().toString())
                }
            }

            override fun onFailure(call: Call<FirebaseToken>, t: Throwable) {
                if (t.message != null)
                    Log.d("add token fail", t.message)
            }
        })
    }
}