package com.jun.clover.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jun.clover.dto.User
import com.jun.clover.api.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository (private val userApi: UserApi) {
    fun registerUser(user : User) {
        userApi.registerUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.e("register user ok", response.code().toString())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("register user fail", t.message!!)
            }
        })
    }

    fun getUser(id : String, user : MutableLiveData<User>) {
        userApi.getUser(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.body() != null) {
                    Log.e("get user okay", response.body().toString())
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                if (t.message != null)
                    Log.e("get user fail", t.message!!)
            }
        })
    }
}