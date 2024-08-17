package com.myprojects.ecommerceapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.model.User

@Database(entities = [Item::class, User::class], version = 1)
abstract class AppDatabase:RoomDatabase(){

    abstract fun getItemDao():ItemDAO
    abstract fun getUserDao(): UserDAO

    companion object{
        @Volatile
        private var instance:AppDatabase?=null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
            synchronized(LOCK){
                instance ?: createDatabase(context).also {
                    instance = it
                }
            }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }
}
