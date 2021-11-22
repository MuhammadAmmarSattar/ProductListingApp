package com.example.myappproject.data.repository.productListing.remote.response.productDetail

import com.example.myappproject.data.repository.productListing.remote.response.BodyDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("status")
    @Expose
    val status: Int,
    @SerializedName("message")
    @Expose
    val message: String,
    @SerializedName("body")
    @Expose
    val body: BodyProductDetailDTO

) {
}