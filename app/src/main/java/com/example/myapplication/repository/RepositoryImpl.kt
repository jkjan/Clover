package com.example.myapplication.repository

class RepositoryImpl : Repository {
    override fun getMyData() = "Hello Koin"
    override fun getTotal() = 1000000
    override fun getNow() = 30
}