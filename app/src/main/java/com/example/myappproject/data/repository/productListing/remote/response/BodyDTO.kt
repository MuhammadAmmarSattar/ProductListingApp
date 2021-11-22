package com.example.myappproject.data.repository.productListing.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BodyDTO(
    @SerializedName("current_page")
    @Expose
    val currentPage: String,
    @SerializedName("data")
    @Expose
    val data: List<DataDTO>,
) {
}