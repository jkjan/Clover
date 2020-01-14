package com.jun.clover.server

import com.jun.clover.dto.User
import retrofit2.Call
import retrofit2.http.GET

interface UserApi {
    @GET("/user/all")
    fun getAllUser() : Call<ArrayList<User>>
}