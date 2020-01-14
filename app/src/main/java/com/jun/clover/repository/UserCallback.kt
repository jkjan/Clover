package com.jun.clover.repository

interface UserCallback {
    fun onSuccess(value : Boolean)
    fun onError()
}