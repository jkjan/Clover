package com.jun.clover.main

import android.util.Log
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jun.clover.R
import com.jun.clover.adapter.MenuButtonsAdapter
import com.jun.clover.adapter.PurchasedCloverAdapter
import com.jun.clover.dto.CloverHistory
import com.jun.clover.dto.CloverValid
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
    private lateinit var _purchasedClover : MutableLiveData<ArrayList<CloverValid>>

    lateinit var mAdapter : MenuButtonsAdapter
    lateinit var mPurchasedCloverAdapter : PurchasedCloverAdapter

    private val _menu = MutableLiveData<Int>()
    var purchasedClover : LiveData<ArrayList<CloverValid>>? = null

    val user : LiveData<User> get() = _user
    val today : LiveData<CloverHistory> get() = _today
    val menu : LiveData<Int> get() = _menu

    fun init(user : User) {
        this.mAdapter = MenuButtonsAdapter(R.layout.item_menu, this)
        this.mPurchasedCloverAdapter = PurchasedCloverAdapter(R.layout.item_purchsed_clover, this)
        this._menu.value = 0
        this._user.value = user
        getTodayClover()
    }

    private fun getTodayClover() {
        cloverHistoryRepository.getTodayClover(_today)
        Log.d("mvm trigger", "works")
    }

    fun getPurchasedClover() {
        _purchasedClover = MutableLiveData()
        purchasedClover = _purchasedClover
        cloverValidRepository.getPurchasedClover(_user.value!!.id, _purchasedClover, mPurchasedCloverAdapter)
    }

    fun updateUI() {
        userRepository.getPoint(user.value!!.id, _user)
        cloverHistoryRepository.getTodayPrize(_today)
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
        cloverValidRepository.purchaseClover(_user.value!!.id)
    }

    fun getCloverNumText(position : Int) : String = purchasedClover!!.value!![position].cloverNum.toString()

    fun getTimeText(position: Int) : String {
        return purchasedClover!!.value!![position].time.substring(11)
    }

    fun goToMenu(position: Int) {
        this._menu.value = position + 1
    }
}