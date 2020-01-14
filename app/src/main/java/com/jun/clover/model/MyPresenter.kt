package com.jun.clover.model

import com.jun.clover.repository.MainRepository

class MyPresenter(private val repository : MainRepository) {
    fun sayHello() = "${repository.getMyData()} from $this"
}