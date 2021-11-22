package com.example.myappproject.data.repository.productListing

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.myappproject.data.repository.productListing.remote.NETWORK_PAGE_SIZE
import com.example.myappproject.data.repository.productListing.remote.request.ProductListingRequest
import com.example.myappproject.data.repository.productListing.remote.response.DataDTO
import com.example.myappproject.data.repository.productListing.remote.service.ProductListingService
import com.example.myappproject.utils.Constants
import java.io.IOException

private const val TMDB_STARTING_PAGE_INDEX = 1
class ProductPagingSource(
    private val productListingService: ProductListingService,
    private val productListingRequest: ProductListingRequest
) : PagingSource<Int, DataDTO>() {

    override fun getRefreshKey(state: PagingState<Int, DataDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataDTO> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val pageNumber = params.key ?: 0
            val response = productListingService.   getProductListing(
                productListingRequest.categoryId,
                pageNumber
            )
//            val product = response.body.data
//            val nextKey =
//                if (product.isEmpty()) {
//                    null
//                } else {
//                    // By default, initial load size = 3 * NETWORK PAGE SIZE
//                    // ensure we're not requesting duplicating items at the 2nd request
//                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
//                }
//            LoadResult.Page(
//                data = response.body.data,
//                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX ) null else pageIndex,
//                nextKey = nextKey
//            )
            LoadResult.Page(
                data = response.body.data,
                prevKey = null,
                nextKey = if (response.body.data.size < Constants.Paging.GET_PHOTO_PAGE_SIZE) null else pageNumber.plus(
                    1
                )
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}