package com.example.onecoin.viewmodel

import android.util.Log
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onecoin.R
import com.example.onecoin.adapter.MenuButtonsAdapter
import com.example.onecoin.repository.MainRepository
import kotlin.math.log10

class MainViewModel(private val mRepo : MainRepository) : ViewModel() {
    private val _total = MutableLiveData<Int>()
    private val _now = MutableLiveData<Int>()
    private val _tickle = MutableLiveData<Boolean>()
    lateinit var mAdapter : MenuButtonsAdapter

    val total : LiveData<Int> get() = _total
    val now : LiveData<Int> get() = _now
    val tickle : LiveData<Boolean> get() = _tickle

    fun init() {
        _tickle.value = false
        this.mAdapter = MenuButtonsAdapter(R.layout.item_menu, this)
        this._total.value = 0
        this._now.value = 0
    }

    fun tickled() {
        _tickle.value = !(_tickle.value!!)
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

    fun setTotal() {
        this._total.value = mRepo.getTotal()
    }

    fun setNow() {
        this._now.value = mRepo.getNow()
    }

    fun getTotalCollected() = this._total.value!!.toString() + " p"

    fun getNowCollected() = this._now.value!!.toString() + " p"
}