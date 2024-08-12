package com.myprojects.ecommerceapp.repository

import com.myprojects.ecommerceapp.database.ItemDatabase
import com.myprojects.ecommerceapp.model.Item

class ItemRepository(private val db: ItemDatabase) {
    suspend fun insertItems(item: Item) = db.getItemDao().InsertItem(item)
    suspend fun updateItems(item: Item) = db.getItemDao().UpdateItem(item)
    suspend fun deleteItems(item: Item) = db.getItemDao().DeleteItem(item)

    fun getAllItems() = db.getItemDao().GetAllItems()
    fun searchItems(query: String?) = db.getItemDao().SearchItem(query)
    fun getItemById(id: Int) = db.getItemDao().GetItemById(id)
}