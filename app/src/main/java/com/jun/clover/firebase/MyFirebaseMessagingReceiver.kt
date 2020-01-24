package com.jun.clover.firebase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jun.clover.main.MainViewModel

class MyFirebaseMessagingReceiver : BroadcastReceiver() {
    lateinit var mainViewModel : MainViewModel

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("mvm trigger", "okay")
        mainViewModel.updateUI()
    }
}