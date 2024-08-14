package com.myprojects.ecommerceapp.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentProfileBinding
import com.myprojects.ecommerceapp.getLoginInfo

class ProfileViewModel(val binding:FragmentProfileBinding, val userViewModel: UserViewModel):ViewModel() {
    private var _userLogName = MutableLiveData<String>()
    private val userLogName get() = _userLogName

    fun setUserLogged(value : String){
        _userLogName.value = value
        if (userLogName.value != ""){
            initProfileMainUI()
            initProfileMainItem()
        } else {
            initProfileLogin()
        }
    }

    private fun initProfileMainItem() {
        val user = userViewModel.getUserByName(userLogName.value.toString())
        if (user != null) {
            binding.apply {
                textViewProfileName.text = user.username
                textViewProfileBalance.text = user.saldo.toString()
            }
        } else {
            Log.w("Account Error","Gagal memuat akun")
        }
    }

    private fun initProfileLogin() {
        binding.apply {
            imageViewProfilePic.visibility = View.GONE
            textViewProfileName.visibility = View.GONE
            textViewProfileName.visibility = View.GONE
            textViewProfileBalance.visibility = View.GONE
            buttonProfileAddItem.visibility = View.GONE
            buttonLogin.visibility = View.VISIBLE
        }
    }

    private fun initProfileMainUI(){
        binding.apply {
            imageViewProfilePic.visibility = View.VISIBLE
            textViewProfileName.visibility = View.VISIBLE
            textViewProfileName.visibility = View.VISIBLE
            textViewProfileBalance.visibility = View.VISIBLE
            buttonProfileAddItem.visibility = View.VISIBLE
            buttonLogin.visibility = View.GONE
        }
    }
}