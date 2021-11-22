package com.example.myappproject.data.repository.productListing.local.productDetail

data class ProductDetail (
    val productId : Int,
    val title : String,
    val desc : String,
    val price : Double,
    val brandName : String,
    val listImages : List<String>
)