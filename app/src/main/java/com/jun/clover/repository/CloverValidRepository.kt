package com.jun.clover.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jun.clover.adapter.PurchasedCloverAdapter
import com.jun.clover.api.CloverValidApi
import com.jun.clover.dto.CloverValid
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

    fun getPurchasedClover(id : String, list : MutableLiveData<ArrayList<CloverValid>>, mPurchasedCloverAdapter: PurchasedCloverAdapter) {
        cloverValidApi.getPurchasedClover(id).enqueue(object : Callback<ArrayList<CloverValid>> {
            override fun onResponse(call: Call<ArrayList<CloverValid>>, response: Response<ArrayList<CloverValid>>) {
                if (response.body() != null) {
                    Log.e("clover list ok", response.body().toString())
                    list.postValue(response.body())
                    mPurchasedCloverAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<CloverValid>>, t: Throwable) {
                if (t.message != null)
                    Log.e("clover list fail", t.message.toString())
            }
        })
    }
}