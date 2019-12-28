package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemMenuBinding
import com.example.myapplication.viewmodel.MainActivityViewModel

class MenuButtonsAdapter (@LayoutRes val layoutID : Int, private val mMainActivityViewModel: MainActivityViewModel) : RecyclerView.Adapter<MenuButtonsAdapter.Holder>() {
    val menuList = arrayListOf(
        "회원정보",
        "설정",
        "친구초대",
        "공지사항",
        "FAQ",
        "서비스 이용약관",
        "개인정보 처리방침",
        "회사소개 및 제휴문의",
        "서비스 사용 방법")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemMenuBinding>(layoutInflater, viewType, parent, false)
        return Holder(binding)
    }

    override fun getItemCount() : Int = menuList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mMainActivityViewModel, position)
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition()

    private fun getLayoutIdForPosition(): Int = layoutID

    inner class Holder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.itemView) {
        fun bind(mMainActivityViewModel: MainActivityViewModel, position: Int) {
            binding.mavm = mMainActivityViewModel
            binding.position = position
            binding.executePendingBindings()
        }
    }
}