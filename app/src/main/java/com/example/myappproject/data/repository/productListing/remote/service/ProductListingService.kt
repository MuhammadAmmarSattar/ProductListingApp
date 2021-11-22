package com.example.myappproject.data.repository.productListing.remote.service

import com.example.myappproject.data.repository.productListing.remote.response.ProductListingResponse
import com.example.myappproject.data.repository.productListing.remote.response.productDetail.ProductDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductListingService {
    @GET("v1/products")
    suspend fun getProductListing(
        @Query("category_id") category_id: Int,
        @Query("page") page: Int,
    ):ProductListingResponse

    @GET("v1/product/{product_id}")
    suspend fun getProductDetail(
        @Path("product_id") product_id: Int,
    ): ProductDetailResponse
}