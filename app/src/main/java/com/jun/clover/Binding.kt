package com.jun.clover

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jun.clover.adapter.MenuButtonsAdapter
import com.jun.clover.adapter.PurchasedCloverAdapter

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