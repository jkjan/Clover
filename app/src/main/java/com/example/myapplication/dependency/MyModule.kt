package com.example.myapplication.dependency

import com.example.myapplication.server.Service
import com.example.myapplication.model.MyPresenter
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.RepositoryImpl
import com.example.myapplication.viewmodel.MainActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val appModule = module {
    single <Repository> {
        RepositoryImpl()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://frozen-cove-44670.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Service::class.java)
    }

    viewModel {
        MainActivityViewModel()
    }

    factory {
        MyPresenter(get())
    }
}