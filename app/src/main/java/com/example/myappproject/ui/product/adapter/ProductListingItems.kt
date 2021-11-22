package com.example.myappproject.ui.product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.myappproject.R
import com.example.myappproject.data.repository.productListing.local.ProductListing
import com.example.myappproject.databinding.RowProductListingItemsBinding

class ProductListingItems (val glide: RequestManager, val callback: ProductClickListener) :
    PagingDataAdapter<ProductListing, ProductListingItems.ProductListingViewHolder>(PhotosDiffCallback()) {

    override fun onBindViewHolder(holder: ProductListingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListingViewHolder =
        ProductListingViewHolder(
            RowProductListingItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    inner class ProductListingViewHolder(val binding: RowProductListingItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productListing: ProductListing) {
            glide.load(productListing.image).into(binding.imageView)
            binding.apply {
                tvTitle.text = "${"Title: "}${productListing.title}"
                tvSubTitle.text = "${"SubTitle: "}${productListing.subTitle}"
                tvPrice.text = "Price: "+productListing.price.toString()
                rootView.setOnClickListener { callback.onProductItemListener(productId = productListing.productId) }
                setAnimation(rootView,bindingAdapterPosition,rootView.context)
            }
        }
    }
    class PhotosDiffCallback : DiffUtil.ItemCallback<ProductListing>() {
        override fun areItemsTheSame(oldItem: ProductListing, newItem: ProductListing): Boolean =
            oldItem.productId == newItem.productId

        override fun areContentsTheSame(oldItem: ProductListing, newItem: ProductListing): Boolean =
            oldItem == newItem
    }
    private fun setAnimation(viewToAnimate: View, position: Int, context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        viewToAnimate.startAnimation(animation)
    }
    interface ProductClickListener {
        fun onProductItemListener(productId:Int)
    }
}



