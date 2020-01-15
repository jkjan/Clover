package com.jun.clover.repository

import android.util.Log
import com.jun.clover.api.CloverValidApi
import dagger.Component
import org.jetbrains.annotations.PropertyKey
import org.koin.dsl.KoinAppDeclaration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CloverValidRepository (private val cloverValidApi: CloverValidApi) {
    fun purchaseClover(id : String) {
        cloverValidApi.purchaseClover(id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null)
                    Log.e("clover purchase ok", response.body().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                if (t.message != null)
                    Log.e("clover purchase fail", t.message.toString())
            }
        })
    }
}