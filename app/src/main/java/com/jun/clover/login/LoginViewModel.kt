package com.jun.clover.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jun.clover.dto.User
import com.jun.clover.dto.UserRegister
import com.jun.clover.repository.UserRepository
import com.kakao.auth.AuthType
import com.kakao.auth.Session

class LoginViewModel(private val mUserRepository: UserRepository) : ViewModel() {
    private val _register = MutableLiveData<Int>()
    private val _user = MutableLiveData<User?>()

    val user : LiveData<User?> get() = _user
    lateinit var id : MutableLiveData<String>
    var name = MutableLiveData<String>()
    lateinit var connApp : MutableLiveData<String>
    var email = MutableLiveData<String>()

    val register : LiveData<Int> get() = _register

    fun init() {
        _register.value = 0
    }

    fun registerInit() {
        id = MutableLiveData()
        name = MutableLiveData()
        connApp = MutableLiveData()
        email = MutableLiveData()
    }

    fun registerUser() {
        if (name.value == null || email.value == null) {
            _register.value = -1
            return
        }

        mUserRepository.registerUser(UserRegister(id.value!!, name.value!!, connApp.value!!, email.value!!, 0))
        _register.value = 1
    }

    fun getUser(id : String) {
        mUserRepository.getUser(id, _user)
    }

    fun kakaoLogin() {

    }
}