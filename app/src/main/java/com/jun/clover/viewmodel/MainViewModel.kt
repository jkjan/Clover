package com.jun.clover.viewmodel

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jun.clover.R
import com.jun.clover.adapter.MenuButtonsAdapter
import com.jun.clover.dto.CloverHistory
import com.jun.clover.dto.User
import com.jun.clover.repository.CloverHistoryRepository
import com.jun.clover.repository.CloverValidRepository
import com.jun.clover.repository.UserRepository
import kotlin.math.log10

class MainViewModel(private val userRepository : UserRepository,
                    private val cloverValidRepository: CloverValidRepository,
                    private val cloverHistoryRepository: CloverHistoryRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    private val _today = MutableLiveData<CloverHistory>()
    lateinit var mAdapter : MenuButtonsAdapter

    val user : LiveData<User> get() = _user
    val today : LiveData<CloverHistory> get() = _today

    fun init() {
        this.mAdapter = MenuButtonsAdapter(R.layout.item_menu, this)
        cloverHistoryRepository.getTodayClover(_today)
        userRepository.getUser("android", _user)
    }

    fun drawerControl(drawer : DrawerLayout) {
        if (!drawer.isDrawerOpen(GravityCompat.START))
            drawer.openDrawer(GravityCompat.START)
        else
            drawer.closeDrawer(GravityCompat.START)
    }

    fun setTextSize(amt : Int) : Float {
        val len = log10(amt.toDouble())
        return if (len <= 3)
            170f
        else
            170f - len.toFloat() * 10f
    }

    fun purchaseClover() {
        cloverValidRepository.purchaseClover(this._user.value!!.id)
    }
}