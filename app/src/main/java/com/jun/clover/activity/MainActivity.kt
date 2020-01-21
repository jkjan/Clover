package com.jun.clover.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.jun.clover.R
import com.jun.clover.databinding.ActivityMainBinding
import com.jun.clover.firebase.MyFirebaseMessagingService
import com.jun.clover.lockscreen.LockScreenService
import com.jun.clover.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {
    private val mMainViewModel : MainViewModel by viewModel()

    val myReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e("do", "something")
            mMainViewModel.getTodayClover()
            mMainViewModel.getUser()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainViewModel.init()
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mvm = mMainViewModel
        startService(Intent(this, LockScreenService::class.java))

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("firebase token fail", task.exception.toString())
                    return@OnCompleteListener
                }
                // get new instance id token
                val token = task.result?.token
                Log.d("firebase token ok", token)
            })

        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        registerReceiver(myReceiver, IntentFilter("INTENT_FILTER"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver)
    }
}