package com.jun.clover.frdrcmd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jun.clover.repository.FrdRecmdRepository

class FrdRecmdViewModel (private val frdRecmdRepository: FrdRecmdRepository) : ViewModel() {
    private lateinit var id : String
    private val _recmdCode = MutableLiveData<String?>()
    val recmdCode : LiveData<String?> get() = _recmdCode

    fun init(id : String) {
        this._recmdCode.value = null
        this.id = id
    }

    fun createFrdRecmd() {
        frdRecmdRepository.createFrdRecmd(this.id, _recmdCode)
    }
}