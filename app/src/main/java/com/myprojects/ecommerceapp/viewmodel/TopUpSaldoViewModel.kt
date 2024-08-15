package com.myprojects.ecommerceapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TopUpSaldoViewModel:ViewModel() {
    private val _saldoSaatIni = MutableLiveData<Int>()
    val saldoSaatIni get() = _saldoSaatIni

    fun setSaldoSaatIni(value: Int) {
        _saldoSaatIni.value = value
    }
}