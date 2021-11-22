package com.example.myappproject.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.myappproject.data.Result
import com.example.myappproject.data.repository.productListing.local.ProductListing
import com.example.myappproject.data.repository.productListing.mapper.transformIntoProductListing
import com.example.myappproject.data.repository.productListing.remote.ProductListingRDS
import com.example.myappproject.data.repository.productListing.remote.request.ProductListingRequest
import com.example.myappproject.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductListingRepository @Inject constructor(private val productListingRDS: ProductListingRDS) :
    ProductListingTest {
    suspend fun getPaginatedProduct(
        productListingRequest: ProductListingRequest
    ): Result<Flow<PagingData<ProductListing>>> {
        val res = productListingRDS.getProductListing(productListingRequest)
        return if (res is Result.Success) {
            Result.Success(res.data.map {
                it.map {
                    transformIntoProductListing(it)
                }
            })
        } else {
            Result.Failure(true, null, null, Constants.Error.SOMETHING_WENT_WRONG)
        }
    }

    override suspend fun paginationForData(productListingRequest: ProductListingRequest): Result<Flow<PagingData<ProductListing>>> {
        val res = productListingRDS.getProductListing(productListingRequest)
        return if (res is Result.Success) {
            Result.Success(res.data.map {
                it.map {
                    transformIntoProductListing(it)
                }
            })
        } else {
            Result.Failure(true, null, null, Constants.Error.SOMETHING_WENT_WRONG)
        }
    }
}