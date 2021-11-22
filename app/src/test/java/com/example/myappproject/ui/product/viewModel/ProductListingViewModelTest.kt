package com.example.myappproject.ui.product.viewModel

import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myappproject.data.repository.ProductListingRepository
import com.example.myappproject.data.repository.productListing.local.ProductListing
import com.example.myappproject.data.repository.productListing.remote.request.ProductListingRequest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.LargeTest
import kotlinx.coroutines.test.runBlockingTest
import org.mockito.Mockito.*


@RunWith(AndroidJUnit4::class)
@LargeTest
class ProductListingViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule() // //for livedata
    private lateinit var viewModel:ProductListingViewModel
    @Mock
    private lateinit var productListingRepository : ProductListingRepository

//    @Mock
//    var productListingService: ProductListingService? = null

    @Mock
    var observer: Observer<PagingData<ProductListing>>? = null
    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel = ProductListingViewModel(ApplicationProvider.getApplicationContext(),productListingRepository)
        viewModel.productListingItem.observeForever(observer!!)
    }

    @Test
    fun getProductListingItem() {
        // Given a fresh TasksViewModel
        // When adding a new task
        runBlockingTest  {
            `when`(
                productListingRepository.getPaginatedProduct(
                    ProductListingRequest(
                        categoryId = 0,
                        page = 0
                    )
                )
            ).thenReturn(null)
        }
        // Then the new task event is triggered
        assertNotNull(viewModel.productListingItem)
        assertTrue(viewModel.productListingItem.hasObservers())
    }

    @Test
    fun testApiFetchDataSuccess() {
        // Mock API response
        runBlockingTest  {
        var res =    `when`(   productListingRepository.getPaginatedProduct(
                ProductListingRequest(
                    categoryId = 8,
                    page = 1
                )
            )).thenReturn( null)
        }


//        viewModel.fetchNews()
//        verify(observer)!!.onChanged(NewsListViewState.LOADING_STATE)
//        verify(observer)!!.onChanged(PagingData<ProductListing>)
    }

    @Test
    fun getProductListing() {
    }
}