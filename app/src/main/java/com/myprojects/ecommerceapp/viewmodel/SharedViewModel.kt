package com.myprojects.ecommerceapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val isBottomNavVisible = MutableLiveData<Boolean>(true)
}