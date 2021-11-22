package com.example.myappproject.data.repository.productListing.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myappproject.data.Result
import com.example.myappproject.data.repository.productListing.ProductPagingSource
import com.example.myappproject.data.repository.productListing.local.productDetail.ProductDetail
import com.example.myappproject.data.repository.productListing.remote.request.ProductListingRequest
import com.example.myappproject.data.repository.productListing.remote.request.productDetail.ProductDetailRequest
import com.example.myappproject.data.repository.productListing.remote.response.DataDTO
import com.example.myappproject.data.repository.productListing.remote.response.ProductListingResponse
import com.example.myappproject.data.repository.productListing.remote.response.productDetail.ProductDetailResponse
import com.example.myappproject.data.repository.productListing.remote.service.ProductListingService
import com.example.myappproject.ui.base.BaseRDS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 1

class ProductListingRDS @Inject constructor(private val productListingService: ProductListingService) :
    BaseRDS() {

    suspend fun getProductListing(productListingRequest: ProductListingRequest
    ): Result<Flow<PagingData<DataDTO>>> =
        safeApiCall {
            Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE
                ),
                pagingSourceFactory = {
                    ProductPagingSource(productListingService,
                        productListingRequest
                    )
                }
            ).flow
        }


    suspend fun getProductDetail(
        productDetailRequest  : ProductDetailRequest
    ): Result<ProductDetailResponse> {
        return safeApiCall {
            productListingService.getProductDetail(productDetailRequest.productId)
        }
    }
}