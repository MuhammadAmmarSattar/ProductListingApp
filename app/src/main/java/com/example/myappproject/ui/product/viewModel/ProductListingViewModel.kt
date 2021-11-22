package com.example.myappproject.ui.product.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myappproject.data.repository.ProductListingRepository
import com.example.myappproject.data.repository.productListing.remote.request.ProductListingRequest
import com.example.myappproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.myappproject.data.Result
import com.example.myappproject.data.repository.productListing.local.ProductListing
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class ProductListingViewModel @Inject constructor(
    application: Application,
    private val productListingRepository: ProductListingRepository,
) : BaseViewModel(application) {

    private val _productListingItem: MutableLiveData<PagingData<ProductListing>> = MutableLiveData()
    val productListingItem: LiveData<PagingData<ProductListing>> = _productListingItem


    init {
        getProductListing()
    }
    fun getProductListing() {
        viewModelScope.launch {
            when (val result = productListingRepository.getPaginatedProduct(
                ProductListingRequest(
                    categoryId = 27
                )
            )) {
                is Result.Success -> {
                    result.data
                        .cachedIn(viewModelScope)
                        .collectLatest {
                            _productListingItem.value = it
                        }
                }
                is Result.Failure -> {
                    showToast("Something Went Wrong...")

                }
            }
        }
    }
    suspend fun refreshProducts() {
        _productListingItem.value = PagingData.empty()
        getProductListing()
    }
}