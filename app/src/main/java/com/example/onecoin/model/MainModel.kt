package com.example.onecoin.model

import com.example.onecoin.repository.MainRepository

class MainModel {
    private val mRepo = MainRepository()
    fun getTotal() = mRepo.getTotal()
    fun getNow() = mRepo.getNow()
}