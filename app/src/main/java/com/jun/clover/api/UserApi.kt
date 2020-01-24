package com.jun.clover.api

import com.jun.clover.dto.User
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface UserApi {
    @POST("/user")
    fun registerUser(@Body user : User) : Call<User>

    @GET("/user/{prcrsId}")
    fun getUser(@Path("prcrsId") id : String) : Call<User>

    @GET("/user/{prcrsId}/point")
    fun getPoint(@Path("prcrsId") id : String) : Call<Int>
}