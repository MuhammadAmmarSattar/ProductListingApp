package com.example.myappproject.data.repository.productListing.remote.request

data class ProductListingRequest(
    val categoryId : Int,
    val page : Int?=null
) {
}