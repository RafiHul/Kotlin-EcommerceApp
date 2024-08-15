package com.myprojects.ecommerceapp.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentProfileBinding
import com.myprojects.ecommerceapp.getLoginInfo
import com.myprojects.ecommerceapp.model.User

class ProfileViewModel(val userViewModel: UserViewModel,val viewLifeCycleOwner: LifecycleOwner):ViewModel() {
    private var _userLogName = MutableLiveData<String>()
    val userLogName: LiveData<String> get() = _userLogName

    fun setUserLogged(value : String){
        _userLogName.value = value
    }
}