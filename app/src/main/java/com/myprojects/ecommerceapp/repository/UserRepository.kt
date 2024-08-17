package com.myprojects.ecommerceapp.repository

import android.content.Context
import com.myprojects.ecommerceapp.database.UserDatabase
import com.myprojects.ecommerceapp.getLoginInfo
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.saveLoginInfo

class UserRepository(val db: UserDatabase) {
    suspend fun registerUsers(user:User) = db.getUserDao().RegisterUser(user)
    suspend fun updateUsers(user:User) = db.getUserDao().UpdateUser(user)
    suspend fun deleteUsers(user:User) = db.getUserDao().DeleteUser(user)
    suspend fun saveLoginData(context: Context,id: Int) = saveLoginInfo(context,id)


    fun login(username:String,password:String) = db.getUserDao().Login(username,password)
    fun getLoginData(context: Context) = getLoginInfo(context)
    fun getUserByName(query: String) = db.getUserDao().GetUserByName(query)
    fun getUserById(query: Int) = db.getUserDao().GetUserById(query)
    fun checkExistUser() = db.getUserDao().CheckUserExist()
}