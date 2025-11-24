package com.example.supermarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    val image: Int,
    var quantity: Int = 0,
    val price: Double
) : Parcelable