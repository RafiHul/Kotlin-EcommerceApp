package com.myprojects.ecommerceapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myprojects.ecommerceapp.repository.ItemRepository

class ItemViewModelFactory(val app:Application,val repository: ItemRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)){
            return ItemViewModel(app,repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}