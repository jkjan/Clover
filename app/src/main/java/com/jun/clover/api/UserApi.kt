package com.jun.clover.api

import com.jun.clover.dto.User
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST("/user")
    fun registerUser(@Body user : User) : Call<User>

    @GET("/user/{id}")
    fun getUser(@Path("id") id : String) : Call<User>
}