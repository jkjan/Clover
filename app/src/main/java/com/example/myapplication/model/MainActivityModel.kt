package com.example.myapplication.model

import com.example.myapplication.repository.RepositoryImpl

class MainActivityModel {
    private val mRepo = RepositoryImpl()
    fun getTotal() = mRepo.getTotal()
    fun getNow() = mRepo.getNow()
}