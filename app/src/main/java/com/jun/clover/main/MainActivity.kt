package com.jun.clover.main

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.jun.clover.R
import com.jun.clover.BaseActivity
import com.jun.clover.databinding.ActivityMainBinding
import com.jun.clover.dto.User
import com.jun.clover.firebase.MyFirebaseMessagingReceiver
import com.jun.clover.frdrcmd.FrdRecmdActivity
import com.jun.clover.lockscreen.LockScreenService
import com.jun.clover.userinfo.UserInfoActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private val mMainViewModel : MainViewModel by viewModel()
    private val myReceiver : MyFirebaseMessagingReceiver by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getParcelableExtra<User?>("user")
        Log.d("user prcrsId", user!!.id)
        mMainViewModel.init(user)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mvm = mMainViewModel
        myReceiver.mainViewModel = mMainViewModel
        startService(Intent(this, LockScreenService::class.java))

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("firebase token fail", task.exception.toString())
                    return@OnCompleteListener
                }
                // get new instance prcrsId token
                val token = task.result?.token
                Log.d("firebase token ok", token)
            })

        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        registerReceiver(myReceiver, IntentFilter("com.jun.clover.SEND_FIREBASE"))

        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(p0: View, p1: Int) {
                when (p1) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        mMainViewModel.getPurchasedClover()
                    }
                }
            }

            override fun onSlide(p0: View, p1: Float) {
            }
        })

        mMainViewModel.menu.observe(this, Observer {
            when (it) {
                1 -> {
                    val intent = Intent(this, UserInfoActivity::class.java)
                    intent.putExtra("user", mMainViewModel.user.value)
                    Log.d("user?", mMainViewModel.user.value!!.id)
                    startActivity(intent)
                }

                3 -> {
                    val intent = Intent(this, FrdRecmdActivity::class.java)
                    intent.putExtra("id", mMainViewModel.user.value!!.id)
                    Log.d("user id?", mMainViewModel.user.value!!.id)
                    startActivity(intent)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver)
    }
}