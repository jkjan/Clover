package com.jun.clover.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jun.clover.api.FrdRecmdApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FrdRecmdRepository (private val frdRecmdApi: FrdRecmdApi) {
    fun createFrdRecmd(idRecmder : String, recmdCode : MutableLiveData<String?>) {
        frdRecmdApi.createFrdRecmd(idRecmder).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null) {
                    Log.d("friend recommend okay", response.body().toString())
                    recmdCode.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                if (t.message != null)
                    Log.d("friend recommend fail", t.message!!)
            }
        })
    }
}