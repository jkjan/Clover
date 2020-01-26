package com.jun.clover.lockscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jun.clover.repository.AdRepository

class LockScreenViewModel(private val adRepo : AdRepository) : ViewModel() {
    fun goToLink() {
        Log.d("링크 열기!", "link")
    }

    fun unlockScreen() {
        Log.d("잠금 해제!", "open")
    }
}