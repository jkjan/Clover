package com.jun.clover.firebase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.jun.clover.viewmodel.MainViewModel

class MyFirebaseMessagingReceiver(private val mainViewModel: MainViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("mvm trigger", "okay")
    }
}