package com.example.onecoin.activity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.onecoin.R
import com.example.onecoin.viewmodel.MainViewModel
import com.example.onecoin.databinding.ActivityMainBinding
import com.example.onecoin.lockscreen.LockScreenService
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private val mMainViewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainViewModel.init()
        mMainViewModel.setTotal()
        mMainViewModel.setNow()
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mvm = mMainViewModel
        startService(Intent(this, LockScreenService::class.java))
        mMainViewModel.tickle.observe(this, Observer {
            toast("구매!")
        })
    }
}