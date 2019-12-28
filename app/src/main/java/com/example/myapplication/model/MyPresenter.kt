package com.example.myapplication.model

import com.example.myapplication.repository.Repository

class MyPresenter(private val repository: Repository) {
    fun sayHello() = "${repository.getMyData()} from $this"
}