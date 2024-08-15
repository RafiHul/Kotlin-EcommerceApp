package com.myprojects.ecommerceapp.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myprojects.ecommerceapp.databinding.FragmentProfileBinding

class ProfileViewModelFactory(val userViewModel: UserViewModel,val viewLifeCycleOwner: LifecycleOwner): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(userViewModel,viewLifeCycleOwner) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}