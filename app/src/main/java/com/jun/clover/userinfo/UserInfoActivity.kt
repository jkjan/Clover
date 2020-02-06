package com.jun.clover.userinfo

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.jun.clover.BaseActivity
import com.jun.clover.R
import com.jun.clover.databinding.ActivityUserInfoBinding
import com.jun.clover.dto.User
import com.jun.clover.dto.UserRegister
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoActivity : BaseActivity() {
    private val userInfoViewModel : UserInfoViewModel by viewModel()
    lateinit var binding : ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        val user = intent.getParcelableExtra<User>("user")
        Log.d("user got", user!!.name)
        userInfoViewModel.init(user!!)
        binding.userInfoVM = userInfoViewModel
    }
}
