package com.example.myappproject.data.repository.productListing.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductListingResponse (
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("message")
    @Expose
    val message: String,
    @SerializedName("body")
    @Expose
    val body: BodyDTO
    )
{
}