package com.example.myapplication

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.MenuButtonsAdapter

@BindingAdapter("android:setVAdapter")
fun RecyclerView.bindVRC (adapter : RecyclerView.Adapter<MenuButtonsAdapter.Holder>) {
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}