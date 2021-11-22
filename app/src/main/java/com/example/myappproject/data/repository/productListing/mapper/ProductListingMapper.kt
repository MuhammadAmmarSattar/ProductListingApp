package com.example.myappproject.data.repository.productListing.mapper

import com.example.myappproject.data.repository.productListing.local.ProductListing
import com.example.myappproject.data.repository.productListing.local.productDetail.ProductDetail
import com.example.myappproject.data.repository.productListing.remote.response.DataDTO
import com.example.myappproject.data.repository.productListing.remote.response.productDetail.BodyProductDetailDTO

fun transformIntoProductListing(dataDTO: DataDTO) =
    dataDTO.let {
        ProductListing(
            productId = it.product_id,
            image = it.thumbnail_url,
            title = it.vendor_name,
            subTitle = it.name,
            price = it.sale_price?:0.0
        )
    }

fun transformIntoProductDetail(bodyProductDetailDTO: BodyProductDetailDTO) =
    bodyProductDetailDTO.let {
        ProductDetail(
            productId = it.product_id,
            title = it.name,
            desc = it.products_description,
            price = it.sale_price?:0.0,
            brandName = it.brand_name,
            listImages = transformImagesList(it.images)

        )
    }

fun transformImagesList( list : List<String>):List<String>{
    val myList = ArrayList<String>()
    list.map {
        myList.add(it)
    }
    return myList
}