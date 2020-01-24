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

    fun getTodayPrize(cloverHistory: MutableLiveData<CloverHistory>) {
        cloverHistoryApi.getTodayPrize().enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.body() != null) {
                    Log.e("today prize ok", response.body().toString())
                    val temp = cloverHistory.value!!
                    temp.prizeClover = response.body()!!
                    cloverHistory.postValue(temp)
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                if (t.message != null)
                    Log.e("today prize fail", t.message!!)
            }
        })
    }
}