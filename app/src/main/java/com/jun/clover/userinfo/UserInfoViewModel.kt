package com.jun.clover.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jun.clover.dto.User
import com.jun.clover.repository.UserRepository

class UserInfoViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val bank = MutableLiveData<String>()
    val acc = MutableLiveData<String>()

    fun init(initializer : User) {
        _user.value = initializer
        name.value = user.value!!.name
        email.value = user.value!!.email
        bank.value = if (user.value!!.bank == null) "은행 이름을 입력해주세요" else user.value!!.bank
        acc.value = if (user.value!!.acc == null) "계좌번호를 입력해주세요" else user.value!!.acc
    }

    fun modifyUserInfo() {
        if (name.value!!.isNotEmpty() && email.value!!.isNotEmpty()) {
            // TODO : 유효한 이메일, 계좌 검사
            val temp = _user.value
            temp!!.name = name.value!!
            temp.email = email.value!!
            temp.bank = bank.value!!
            temp.acc = acc.value!!
            userRepository.modifyUserInfo(temp)
        }
    }
}