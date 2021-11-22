package com.example.myappproject.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.neomads.utils.extensions.show
import com.app.neomads.utils.extensions.withLoadStateAdapters
import com.bumptech.glide.RequestManager
import com.example.myappproject.R
import com.example.myappproject.databinding.FragmentProductListBinding
import com.example.myappproject.ui.base.BaseFragment
import com.example.myappproject.ui.component.defaultLoadStateAdapter.DefaultLoadStateAdapter
import com.example.myappproject.ui.product.adapter.ProductListingItems
import com.example.myappproject.ui.product.viewModel.ProductListingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding>() , View.OnClickListener {
    @Inject
    lateinit var glide: RequestManager
    private val productListingViewModel: ProductListingViewModel by viewModels()
    private lateinit var productListingItems: ProductListingItems
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProductListBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.imgBack.setOnClickListener(this)
        subscribeUiEvents(productListingViewModel)
        rvSetUp()
        subscribeToObservables()
        applicationExitDialog()
        setupSwipeRefresh()
    }

    private fun subscribeToObservables() {
        productListingViewModel.productListingItem.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                showLoader(false)
                productListingItems.submitData(it)
            }
        }


    }

    private fun rvSetUp() {
        val footerAdapter = DefaultLoadStateAdapter()
        val headerAdapter = DefaultLoadStateAdapter()
        productListingItems = ProductListingItems(glide,
            object : ProductListingItems.ProductClickListener {
                override fun onProductItemListener(productId: Int) {
                    navigateByDirections(
                        ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                            productId = productId
                        )
                    )
                }
            })
        binding.rvProductListing.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = productListingItems.withLoadStateAdapters(
                footer = footerAdapter,
                header = headerAdapter,
                callback = {
                    binding.tvPlaceHolder.show(it)
                }
            )
        }
    }
    private fun applicationExitDialog() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    showAlert(
                        message = getString(R.string.error_msg),
                        textPositive = getString(R.string.okay),
                        textNegative = getString(R.string.cancel),
                        actionNegative = {
                        },
                        actionPositive = {
                            activity.finish()
                        }
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            lifecycleScope.launch {
                showLoader(true)
                productListingViewModel.refreshProducts()
            }
        }
    }


    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.imgBack ->{
                back()
            }
        }
    }
}