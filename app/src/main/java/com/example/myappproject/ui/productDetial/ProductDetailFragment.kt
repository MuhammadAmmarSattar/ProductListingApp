package com.example.myappproject.ui.productDetial

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.RequestManager
import com.example.myappproject.databinding.FragmentProductDetailBinding
import com.example.myappproject.ui.base.BaseFragment
import com.example.myappproject.ui.productDetial.adapter.ProductDetailItems
import com.example.myappproject.ui.productDetial.viewModel.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {
    private val args: ProductDetailFragmentArgs by navArgs()
    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private lateinit var productDetailItems: ProductDetailItems

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProductDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(productDetailViewModel)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        productDetailViewModel.productDetail.observe(viewLifecycleOwner) {
            binding.viewModel = productDetailViewModel
            binding.fragment = this
//            binding.tvDesc.text = HtmlCompat.fromHtml(it.desc, HtmlCompat.FROM_HTML_MODE_LEGACY)
            rvSetUp(it.listImages)
        }
    }
    private fun rvSetUp(list : List<String>){
        binding.rvImgs.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            productDetailItems = ProductDetailItems(list)
            isNestedScrollingEnabled = false
            adapter = productDetailItems
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(this)
            binding.indicator.attachToRecyclerView(this, pagerSnapHelper)
            }
    }
//    fun backPress(){
//        back()
//    }
}