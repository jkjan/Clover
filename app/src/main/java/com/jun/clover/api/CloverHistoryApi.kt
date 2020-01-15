package com.jun.clover.api

import com.jun.clover.dto.CloverHistory
import retrofit2.Call
import retrofit2.http.GET

interface CloverHistoryApi {
    @GET("/history/today")
    fun getTodayClover() : Call<CloverHistory>
}