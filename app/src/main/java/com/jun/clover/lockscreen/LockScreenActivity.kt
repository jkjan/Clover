package com.jun.clover.lockscreen

import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.jun.clover.R
import com.jun.clover.BaseActivity
import com.jun.clover.databinding.ActivityLockScreenBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LockScreenActivity : BaseActivity() {
    private val mLockScreenViewModel : LockScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager?
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        when {
            android.os.Build.VERSION.SDK_INT >= 27 -> {
                setShowWhenLocked(true)
                setTurnScreenOn(true)
                keyguardManager?.requestDismissKeyguard(this, null)
            }
            android.os.Build.VERSION.SDK_INT == 26 -> {
                window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
                window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
                keyguardManager?.requestDismissKeyguard(this, null)
            }
            else -> {
                window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
                window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
                window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
            }
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val binding = DataBindingUtil.setContentView<ActivityLockScreenBinding>(this, R.layout.activity_lock_screen)
        binding.lsVM = mLockScreenViewModel
        binding.lifecycleOwner = this
        //binding.swipeBtn.mLockScreenViewModel = mLockScreenViewModel
    }
}
