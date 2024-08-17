package com.myprojects.ecommerceapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myprojects.ecommerceapp.repository.AppRepository

class AppViewModelFactory(val app:Application, val repository: AppRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)){
            return AppViewModel(app,repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}