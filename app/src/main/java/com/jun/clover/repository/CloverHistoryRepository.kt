package com.jun.clover.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jun.clover.api.CloverHistoryApi
import com.jun.clover.dto.CloverHistory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CloverHistoryRepository (private var cloverHistoryApi: CloverHistoryApi) {
    fun getTodayClover(cloverHistory : MutableLiveData<CloverHistory>) {
        cloverHistoryApi.getTodayClover().enqueue(object : Callback<CloverHistory> {
            override fun onResponse(call: Call<CloverHistory>, response: Response<CloverHistory>) {
                if (response.body() != null) {
                    Log.e("today clover ok", response.body().toString())
                    cloverHistory.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<CloverHistory>, t: Throwable) {
                if (t.message != null)
                    Log.e("today clover fail", t.message!!)
            }
        })
    }
}