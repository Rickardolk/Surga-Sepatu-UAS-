package com.example.surgasepatu.Item

import retrofit2.http.Url

data class Product_kt(
    val image: String,
    val brand: String,
    val name: String,
    val size: String,
    val status: String,
    val oldPrice: String,
    val newPrice: String,
    val rating: String
)
