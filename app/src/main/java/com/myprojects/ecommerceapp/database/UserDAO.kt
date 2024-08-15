package com.myprojects.ecommerceapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.myprojects.ecommerceapp.model.User

@Dao
interface UserDAO {
    @Insert
    suspend fun RegisterUser(user: User)

    @Update
    suspend fun UpdateUser(user: User)

    @Delete
    suspend fun DeleteUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun Login(username:String,password:String): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE username == :query")
    fun GetUserByName(query:String): LiveData<User>?

    @Query("SELECT * FROM users WHERE id == :query")
    fun GetUserById(query:Int): LiveData<User>?

    @Query("SELECT username FROM users")
    fun CheckUserExist(): LiveData<List<String>>
}