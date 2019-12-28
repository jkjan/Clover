package com.example.myapplication.repository

interface Repository {
    fun getMyData() : String
    fun getTotal() : Int
    fun getNow() : Int
}