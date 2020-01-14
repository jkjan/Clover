package com.jun.clover.repository

import com.jun.clover.server.UserApi

class UserRepository (private val userApi: UserApi) {
    fun getUserList() = userApi.getAllUser()
}