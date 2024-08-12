package com.myprojects.ecommerceapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.myprojects.ecommerceapp.model.Item

@Dao
interface ItemDAO {

    @Insert
    suspend fun InsertItem(item: Item)

    @Update
    suspend fun UpdateItem(item: Item)

    @Delete
    suspend fun DeleteItem(item: Item)

    @Query("SELECT * FROM items")
    fun GetAllItems():LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE id == :query")
    fun GetItemById(query:Int):LiveData<Item>

    @Query("SELECT * FROM items WHERE nameItem LIKE:query")
    fun SearchItem(query:String?):LiveData<List<Item>>
}