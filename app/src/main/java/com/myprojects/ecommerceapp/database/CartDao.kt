package com.myprojects.ecommerceapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Update
import com.myprojects.ecommerceapp.model.Cart
import com.myprojects.ecommerceapp.model.Item

@Dao
interface CartDao {
    @Insert
    suspend fun InsertCart(cart: Cart)

    @Update
    suspend fun UpdateCart(cart: Cart)

    @Delete
    suspend fun DeleteCart(cart: Cart)

    @Query("SELECT * FROM carts WHERE carts.itemId LIKE :itemId")
    fun CheckExistItemCart(itemId: Int): LiveData<Cart>?

    @Query("SELECT items.*, carts.* FROM carts LEFT JOIN items ON carts.itemId = items.id WHERE carts.userId = :userId")
    fun getCartUser(userId: Int): LiveData<List<CartWithItemAndUser>>

    data class CartWithItemAndUser (
        @Embedded
        val cart: Cart,
        @Relation(parentColumn = "itemId", entityColumn = "id")
        val item: Item
    )
}