package com.myprojects.ecommerceapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val app: Application,
                    private val repository: ItemRepository)
    :AndroidViewModel(app) {

        fun insertItems(item:Item) = viewModelScope.launch {
            repository.insertItems(item)
        }

        fun updateItems(item:Item) = viewModelScope.launch {
            repository.updateItems(item)
        }

        fun deleteItems(item:Item) = viewModelScope.launch {
            repository.deleteItems(item)
        }

        fun getAllItems() = repository.getAllItems()

        fun searchItems(query: String?) = repository.searchItems(query)
}