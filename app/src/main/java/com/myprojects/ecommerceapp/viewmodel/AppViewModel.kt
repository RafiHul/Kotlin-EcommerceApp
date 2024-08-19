package com.myprojects.ecommerceapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.ecommerceapp.model.Cart
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.repository.AppRepository
import kotlinx.coroutines.launch

class AppViewModel(private val app: Application,
                   private val repository: AppRepository)
    :AndroidViewModel(app) {
        //For Items
        fun insertItems(item:Item) = viewModelScope.launch {
            repository.insertItems(item)
        }
        fun updateItems(item:Item) = viewModelScope.launch {
            repository.updateItems(item)
        }
        fun deleteItems(item:Item) = viewModelScope.launch {
            repository.deleteItems(item)
        }
        //For Users
        fun registerUser(user: User) = viewModelScope.launch {
            repository.registerUsers(user)
        }
        fun updateUser(user: User) = viewModelScope.launch {
            repository.updateUsers(user)
        }
        fun deleteUser(user: User) = viewModelScope.launch {
            repository.deleteUsers(user)
        }
        fun saveLoginData(context: Context, id: Int) = viewModelScope.launch {
            repository.saveLoginData(context,id)
        }
        //For Cart
        fun insertCart(cart: Cart) = viewModelScope.launch {
        repository.deleteCarts(cart)
        }
        fun updateCart(cart: Cart) = viewModelScope.launch {
            repository.deleteCarts(cart)
        }
        fun deleteCart(cart: Cart) = viewModelScope.launch {
            repository.deleteCarts(cart)
        }

        //For Items
        fun getAllItems() = repository.getAllItems()
        fun getItemById(id:Int) = repository.getItemById(id)
        fun searchItems(query: String?) = repository.searchItems(query)
        //For Users
        fun login(username:String,password:String) = repository.login(username, password)
        fun getLoginData(context: Context) = repository.getLoginData(context)
        fun getUserByName(query:String) = repository.getUserByName(query)
        fun getUserById(id:Int) = repository.getUserById(id)
        fun checkExitsUser() = repository.checkExistUser()
        //For Cart
        fun getCartUser(userid: Int) = repository.getCartUser(userid)
}