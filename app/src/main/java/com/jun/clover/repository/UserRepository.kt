package com.jun.clover.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jun.clover.dto.User
import com.jun.clover.api.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

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
        Log.d("user prcrsId", id)

        userApi.getUser(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.body() != null) {
                    Log.e("get user okay", response.body().toString())
                    user.postValue(response.body()!!)
                }

                else {
                    user.postValue(User("null", "null", "null", "null", 0))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                if (t.message != null)
                    Log.e("get user fail", t.message!!)
            }
        })
    }

    fun getPoint(id : String, user : MutableLiveData<User>) {
        userApi.getPoint(id).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.body() != null) {
                    Log.d("get user point okay", response.body().toString())
                    val temp = user.value!!
                    temp.point = response.body()!!
                    user.postValue(temp)
                }
                else {
                    Log.d("user point", "null")
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                if (t.message != null)
                    Log.e("get user point fail", t.message!!)
                else {
                    Log.e("get user point fail", "no message")
                }
            }
        })
    }
}