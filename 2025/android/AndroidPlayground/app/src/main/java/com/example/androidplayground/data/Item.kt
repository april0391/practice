package com.example.androidplayground.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity(tableName = "items")
data class Item(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int
)

fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}