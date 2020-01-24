package com.jun.clover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jun.clover.databinding.ItemMenuBinding
import com.jun.clover.databinding.ItemPurchsedCloverBinding
import com.jun.clover.main.MainViewModel

class PurchasedCloverAdapter (@LayoutRes val layoutID : Int, private val mMainViewModel: MainViewModel): RecyclerView.Adapter<PurchasedCloverAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemPurchsedCloverBinding>(layoutInflater, viewType, parent, false)
        return Holder(binding)
    }

    override fun getItemCount() : Int = if (mMainViewModel.purchasedClover == null) 0 else mMainViewModel.purchasedClover!!.value!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mMainViewModel, position)
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition()

    private fun getLayoutIdForPosition(): Int = layoutID

    inner class Holder(private val binding: ItemPurchsedCloverBinding) :
        RecyclerView.ViewHolder(binding.itemView) {
        fun bind(mMainViewModel: MainViewModel, position: Int) {
            binding.mavm = mMainViewModel
            binding.position = position
            binding.executePendingBindings()
        }
    }
}