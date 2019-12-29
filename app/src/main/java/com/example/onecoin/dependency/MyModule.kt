package com.example.onecoin.dependency

import com.example.onecoin.server.RetroFitAPI
import com.example.onecoin.model.MyPresenter
import com.example.onecoin.repository.AdRepository
import com.example.onecoin.repository.MainRepository
import com.example.onecoin.lockscreen.LockScreenReceiver
import com.example.onecoin.lockscreen.LockScreenService
import com.example.onecoin.lockscreen.RestartReceiver
import com.example.onecoin.viewmodel.LockScreenViewModel
import com.example.onecoin.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://frozen-cove-44670.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetroFitAPI::class.java)
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
        MainRepository()
    }

    factory {
        AdRepository()
    }
}