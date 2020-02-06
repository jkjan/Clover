package com.jun.clover.dependency

import com.jun.clover.api.*
import com.jun.clover.firebase.MyFirebaseMessagingReceiver
import com.jun.clover.firebase.MyFirebaseMessagingService
import com.jun.clover.frdrcmd.FrdRecmdViewModel
import com.jun.clover.lockscreen.LockScreenReceiver
import com.jun.clover.lockscreen.LockScreenService
import com.jun.clover.lockscreen.RestartReceiver
import com.jun.clover.lockscreen.LockScreenViewModel
import com.jun.clover.login.LoginViewModel
import com.jun.clover.main.MainViewModel
import com.jun.clover.repository.*
import com.jun.clover.userinfo.UserInfoViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

fun getRetrofit() : Retrofit{
    return Retrofit.Builder()
        .baseUrl("http://211.184.108.31:8080")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val appModule = module {
    single {
        getRetrofit().create(UserApi::class.java)
    }

    single {
        getRetrofit().create(CloverHistoryApi::class.java)
    }

    single {
        getRetrofit().create(CloverValidApi::class.java)
    }

    single {
        getRetrofit().create(FirebaseTokenApi::class.java)
    }

    single {
        getRetrofit().create(FrdRecmdApi::class.java)
    }

    single {
        LockScreenReceiver()
    }

    single {
        RestartReceiver()
    }

    single {
        LockScreenService()
    }

    single {
        MyFirebaseMessagingService()
    }

    single {
        MyFirebaseMessagingReceiver()
    }

    viewModel {
        MainViewModel(get(), get(), get())
    }

    viewModel {
        LockScreenViewModel(get())
    }

    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        UserInfoViewModel(get())
    }

    viewModel {
        FrdRecmdViewModel(get())
    }

    factory {
        AdRepository()
    }

    factory {
        UserRepository(get())
    }

    factory {
        CloverValidRepository(get())
    }

    factory {
        CloverHistoryRepository(get())
    }

    factory {
        FirebaseTokenRepository(get())
    }

    factory {
        FrdRecmdRepository(get())
    }
}