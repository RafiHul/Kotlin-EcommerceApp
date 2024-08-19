package com.myprojects.ecommerceapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "items")
@Parcelize
data class Item (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val nameItem:String,
    val price:Double,
    val description:String,
    val quantity:Int,
    val owner_id:Int
): Parcelable