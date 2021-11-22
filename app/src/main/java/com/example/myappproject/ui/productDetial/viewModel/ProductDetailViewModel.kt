package com.example.myappproject.ui.productDetial.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.myappproject.data.Result
import com.example.myappproject.data.repository.productListing.ProductDetailRepository
import com.example.myappproject.data.repository.productListing.local.productDetail.ProductDetail
import com.example.myappproject.data.repository.productListing.remote.request.productDetail.ProductDetailRequest
import com.example.myappproject.ui.base.BaseViewModel
import com.example.myappproject.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    application: Application,
    private val productDetailRepository: ProductDetailRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(application) {
    private val _productDetail : MutableLiveData<ProductDetail> = MutableLiveData()
    val productDetail: LiveData<ProductDetail> = _productDetail
    val brandName = MutableLiveData<String>()
    val isEmail = MutableLiveData(String)
    init {
        getProductDetail()
    }

  private  fun getProductDetail() {
        savedStateHandle.get<Int>(Constants.Bundle.PRODUCT_ID)?.let {
            viewModelScope.launch {
                showLoader(true)
                val result = productDetailRepository.getDetails(
                    ProductDetailRequest(
                        productId = it
                    )
                )
                when (result) {
                            is Result.Success -> {
                                showLoader(false)
                                result.data.let {
                                    brandName.value = it.brandName
                            _productDetail.value = it
                        }
                    }
                    is Result.Failure -> {
                        showLoader(false)
                        showToast(result.errorMessage)
                    }
                }
                showLoader(false)
            }
        }
    }
}