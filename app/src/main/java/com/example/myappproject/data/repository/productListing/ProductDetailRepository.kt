package com.example.myappproject.data.repository.productListing

import com.example.myappproject.data.Result
import com.example.myappproject.data.repository.productListing.local.productDetail.ProductDetail
import com.example.myappproject.data.repository.productListing.mapper.transformIntoProductDetail
import com.example.myappproject.data.repository.productListing.remote.ProductListingRDS
import com.example.myappproject.data.repository.productListing.remote.request.productDetail.ProductDetailRequest
import javax.inject.Inject

class ProductDetailRepository @Inject constructor(private val productListingRDS: ProductListingRDS) {

    suspend fun getDetails(
        productDetailRequest: ProductDetailRequest
    ): Result<ProductDetail> {
        return when (val result =
            productListingRDS.getProductDetail(productDetailRequest)) {
            is Result.Success -> {
                if (result.data.status == 200) {
                    Result.Success(transformIntoProductDetail(result.data.body))
                } else {
                    Result.Failure(false, null, null, result.data.message)
                }
            }
            is Result.Failure -> {
                Result.Failure(
                    false,
                    null,
                    null,
                    result.errorMessage
                )
            }
        }
    }
}