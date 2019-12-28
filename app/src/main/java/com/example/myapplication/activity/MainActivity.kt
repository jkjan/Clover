package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.myapplication.model.MyPresenter
import com.example.myapplication.R
import com.example.myapplication.viewmodel.MainActivityViewModel
import com.example.myapplication.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private val mMainActivityViewModel : MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityViewModel.init()
        mMainActivityViewModel.setTotal()
        mMainActivityViewModel.setNow()
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mavm = mMainActivityViewModel

        mMainActivityViewModel.tickle.observe(this, Observer {
            toast("Tickled!")
        })
    }
}
