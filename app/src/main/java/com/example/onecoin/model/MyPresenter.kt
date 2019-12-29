package com.example.onecoin.model

import com.example.onecoin.repository.MainRepository

class MyPresenter(private val repository : MainRepository) {
    fun sayHello() = "${repository.getMyData()} from $this"
}