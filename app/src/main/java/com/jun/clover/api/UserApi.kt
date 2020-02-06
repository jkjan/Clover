package com.jun.clover.api

import com.jun.clover.dto.User
import com.jun.clover.dto.UserRegister
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST("/user")
    fun registerUser(@Body userRegister : UserRegister) : Call<UserRegister>

    @GET("/user/{prcrsId}")
    fun getUser(@Path("prcrsId") id : String) : Call<User>

    @GET("/user/{prcrsId}/point")
    fun getPoint(@Path("prcrsId") id : String) : Call<Int>

    @PATCH("/user/{id}")
    fun modifyUserInfo(@Path ("id") id : String, @Body user : User) : Call<User>
}