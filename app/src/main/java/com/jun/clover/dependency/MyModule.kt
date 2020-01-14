package com.jun.clover.dependency

import com.jun.clover.server.UserApi
import com.jun.clover.repository.AdRepository
import com.jun.clover.repository.MainRepository
import com.jun.clover.lockscreen.LockScreenReceiver
import com.jun.clover.lockscreen.LockScreenService
import com.jun.clover.lockscreen.RestartReceiver
import com.jun.clover.repository.UserRepository
import com.jun.clover.viewmodel.LockScreenViewModel
import com.jun.clover.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("http://61.102.151.132:8080")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
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

    viewModel {
        MainViewModel(get())
    }

    viewModel {
        LockScreenViewModel(get())
    }

    factory {
        MainRepository(get())
    }

    factory {
        AdRepository()
    }

    factory {
        UserRepository(get())
    }
}