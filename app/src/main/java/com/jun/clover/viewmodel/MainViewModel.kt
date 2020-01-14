package com.jun.clover.viewmodel

import android.util.Log
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jun.clover.R
import com.jun.clover.adapter.MenuButtonsAdapter
import com.jun.clover.dto.User
import com.jun.clover.repository.MainRepository
import kotlin.math.log10

class MainViewModel(private val mRepo : MainRepository) : ViewModel() {
    private val _total = MutableLiveData<Int>()
    private val _now = MutableLiveData<Int>()
    private val _tickle = MutableLiveData<Boolean>()
    lateinit var mAdapter : MenuButtonsAdapter

    private val _users = MutableLiveData<ArrayList<User>>()
    val users : LiveData<ArrayList<User>> get() = _users

    val usertest = MutableLiveData<String>()
    val total : LiveData<Int> get() = _total
    val now : LiveData<Int> get() = _now
    val tickle : LiveData<Boolean> get() = _tickle

    fun init() {
        _tickle.value = false
        this.mAdapter = MenuButtonsAdapter(R.layout.item_menu, this)
        this._total.value = 0
        this._now.value = 0
        mRepo.getUserList(this.usertest)
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

    private fun getUserList() {
        if (this.users.value == null) {
            return
        }
        var text = ""
        for (i in this.users.value!!.indices) {
            text = this.users.value!![i].id +
                    " " + this.users.value!![i].name +
                    " " + this.users.value!![i].connApp + "\n"
        }
        this.usertest.postValue(text)
        Log.e("huh?", this.usertest.value!!)
    }
}