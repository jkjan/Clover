package com.jun.clover.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jun.clover.dto.User
import com.jun.clover.server.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val userApi: UserApi) {

    fun getMyData() = "Hello Koin"
    fun getTotal() = 1000000
    fun getNow() = 30

    fun getUserList(userList : MutableLiveData<String>) {
        userApi.getAllUser().enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.body() != null) {
                    Log.e("user list succeed", response.body()!!.toString())
                    var text = ""
                    for (i in response.body()!!.indices) {
                        text += response.body()!![i].id + " " + response.body()!![i].name + " " + response.body()!![i].connApp + "\n"
                    }
                    userList.postValue(text)
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("user list fail", t.message!!)
            }
        })
        Log.e("this log", "shouldn't be above the logs in onResponse ")
    }
}