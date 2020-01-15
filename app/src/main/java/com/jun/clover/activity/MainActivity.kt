package com.jun.clover.activity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jun.clover.R
import com.jun.clover.viewmodel.MainViewModel
import com.jun.clover.databinding.ActivityMainBinding
import com.jun.clover.lockscreen.LockScreenService
import com.jun.clover.api.UserApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private val mMainViewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainViewModel.init()
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mvm = mMainViewModel
        startService(Intent(this, LockScreenService::class.java))
    }
}