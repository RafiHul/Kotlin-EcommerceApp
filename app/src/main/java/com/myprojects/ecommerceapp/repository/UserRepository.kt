package com.myprojects.ecommerceapp.repository

import com.myprojects.ecommerceapp.database.UserDatabase
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.model.User

class UserRepository(val db: UserDatabase) {
    suspend fun registerUsers(user:User) = db.getUserDao().RegisterUser(user)
    suspend fun updateUsers(user:User) = db.getUserDao().UpdateUser(user)
    suspend fun deleteUsers(user:User) = db.getUserDao().DeleteUser(user)
    suspend fun login(username:String,password:String) = db.getUserDao().Login(username,password)


    fun getUserByName(query: String) = db.getUserDao().GetUserByName(query)
    fun getUserById(query: Int) = db.getUserDao().GetUserById(query)
    fun checkExistUser() = db.getUserDao().CheckUserExist()
}