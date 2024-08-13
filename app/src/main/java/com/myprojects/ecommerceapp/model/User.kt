package com.myprojects.ecommerceapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var username:String,
    var password:String,
    var saldo:Int
):Parcelable