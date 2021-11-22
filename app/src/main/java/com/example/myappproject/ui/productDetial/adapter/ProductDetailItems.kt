package com.example.myappproject.ui.productDetial.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myappproject.databinding.RowProductDetailItemsBinding

class ProductDetailItems(private val images: List<String>) :
    RecyclerView.Adapter<ProductDetailItems.ProductDetailItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailItemsViewHolder =
        ProductDetailItemsViewHolder(
            RowProductDetailItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(
        holder: ProductDetailItems.ProductDetailItemsViewHolder,
        position: Int
    ) {
        holder.bind(images)
    }

    override fun getItemCount() =images.size
    inner class ProductDetailItemsViewHolder(val binding: RowProductDetailItemsBinding)  :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(images: List<String>) {
            binding.imgUrl = images[absoluteAdapterPosition]
        }
        }
}