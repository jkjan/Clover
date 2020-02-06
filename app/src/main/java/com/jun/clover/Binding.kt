package com.jun.clover

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jun.clover.adapter.MenuButtonsAdapter
import com.jun.clover.adapter.PurchasedCloverAdapter
import com.jun.clover.lockscreen.LockScreenViewModel
import com.jun.clover.lockscreen.SwipeButton

@BindingAdapter("android:setVAdapter")
fun RecyclerView.bindVRC (adapter : RecyclerView.Adapter<MenuButtonsAdapter.Holder>) {
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}

@BindingAdapter("android:setPurchasedCloverAdapter")
fun RecyclerView.bindPurchasedCloverAdapter (adapter: RecyclerView.Adapter<PurchasedCloverAdapter.Holder>) {
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}

@BindingAdapter("android:lockscreen")
fun SwipeButton.bindLockScreenViewModel (vm : LockScreenViewModel) {
    Log.d("lockscreen bind", "okay")
    this.mLockScreenViewModel = vm
}