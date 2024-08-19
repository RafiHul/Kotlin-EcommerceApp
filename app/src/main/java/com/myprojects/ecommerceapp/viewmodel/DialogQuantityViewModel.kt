package com.myprojects.ecommerceapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogQuantityViewModel:ViewModel() {
    val total = MutableLiveData<Int>()
    init {
        total.value = 0
    }
}