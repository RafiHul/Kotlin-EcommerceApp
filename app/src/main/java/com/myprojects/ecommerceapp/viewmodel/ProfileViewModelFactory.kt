package com.myprojects.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myprojects.ecommerceapp.databinding.FragmentProfileBinding

class ProfileViewModelFactory(val binding: FragmentProfileBinding,val userViewModel: UserViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(binding, userViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}