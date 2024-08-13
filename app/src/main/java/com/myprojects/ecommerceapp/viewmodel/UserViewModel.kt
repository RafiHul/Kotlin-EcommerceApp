package com.myprojects.ecommerceapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(app: Application,
                    private val repository: UserRepository
): AndroidViewModel(app) {
    fun registerUser(user: User) = viewModelScope.launch {
        repository.registerUsers(user)
    }

    fun updateUser(user:User) = viewModelScope.launch {
        repository.updateUsers(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        repository.deleteUsers(user)
    }

    fun login(username:String,password:String) = viewModelScope.launch {
        repository.login(username, password)
    }

    fun getUserByName(query:String) = repository.getUserByName(query)
    fun getUserById(id:Int) = repository.getUserById(id)
    fun checkExitsUser() = repository.checkExistUser()

}