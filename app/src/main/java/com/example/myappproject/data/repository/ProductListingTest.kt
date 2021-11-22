package com.example.myappproject.data.repository

import androidx.paging.PagingData
import com.example.myappproject.data.Result
import com.example.myappproject.data.repository.productListing.local.ProductListing
import com.example.myappproject.data.repository.productListing.remote.request.ProductListingRequest
import com.example.myappproject.data.repository.productListing.remote.request.productDetail.ProductDetailRequest
import com.example.myappproject.data.repository.productListing.remote.response.productDetail.ProductDetailResponse
import kotlinx.coroutines.flow.Flow

interface ProductListingTest {
    suspend fun paginationForData(productListingRequest : ProductListingRequest) : Result<Flow<PagingData<ProductListing>>>
}