package com.myprojects.ecommerceapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "carts")
@Parcelize
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val itemId: Int,
    var quantity: Int,
    var price: Double,
    val userId: Int
): Parcelable