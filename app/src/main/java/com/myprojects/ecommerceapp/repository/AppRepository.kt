package com.myprojects.ecommerceapp.repository

import android.content.Context
import com.myprojects.ecommerceapp.database.AppDatabase
import com.myprojects.ecommerceapp.getLoginInfo
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.saveLoginInfo

class AppRepository(private val db: AppDatabase) {
    // For Items
    suspend fun insertItems(item: Item) = db.getItemDao().InsertItem(item)
    suspend fun updateItems(item: Item) = db.getItemDao().UpdateItem(item)
    suspend fun deleteItems(item: Item) = db.getItemDao().DeleteItem(item)
    //For Users
    suspend fun registerUsers(user: User) = db.getUserDao().RegisterUser(user)
    suspend fun updateUsers(user: User) = db.getUserDao().UpdateUser(user)
    suspend fun deleteUsers(user: User) = db.getUserDao().DeleteUser(user)
    suspend fun saveLoginData(context: Context, id: Int) = saveLoginInfo(context,id)

    // For Items
    fun getAllItems() = db.getItemDao().GetAllItems()
    fun searchItems(query: String?) = db.getItemDao().SearchItem(query)
    fun getItemById(id: Int) = db.getItemDao().GetItemById(id)
    //For Users
    fun login(username:String,password:String) = db.getUserDao().Login(username,password)
    fun getLoginData(context: Context) = getLoginInfo(context)
    fun getUserByName(query: String) = db.getUserDao().GetUserByName(query)
    fun getUserById(query: Int) = db.getUserDao().GetUserById(query)
    fun checkExistUser() = db.getUserDao().CheckUserExist()
}