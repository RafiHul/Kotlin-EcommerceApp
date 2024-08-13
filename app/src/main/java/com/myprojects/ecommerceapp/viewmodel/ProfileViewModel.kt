package com.myprojects.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentProfileBinding

class ProfileViewModel(val binding:FragmentProfileBinding):ViewModel() {

    fun addItemButton(){
        binding.buttonProfileAddItem.setOnClickListener{
            it.findNavController().navigate(R.id.action_profileFragment_to_newItemFragment)
        }
    }

    fun registerButton(){
        binding.buttonRegister.setOnClickListener{
            it.findNavController().navigate(R.id.action_profileFragment_to_registerFragment)
        }
    }
}