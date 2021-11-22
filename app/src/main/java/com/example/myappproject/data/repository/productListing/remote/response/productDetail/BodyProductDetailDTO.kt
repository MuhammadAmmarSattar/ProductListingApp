package com.example.myappproject.data.repository.productListing.remote.response.productDetail

data class BodyProductDetailDTO(
    val attributes: List<Attribute>,
    val attributes_options: List<AttributesOption>,
    val base_price: Double,
    val brand_name: String,
    val category: List<String>,
    val custom_question: Any,
    val custom_title: Any,
    val delivery: String,
    val experience_info: List<ExperienceInfo>,
    val final_price_base: Double,
    val final_price_sale: Double?,
    val google_place_id: String,
    val images: List<String>,
    val images_low: List<ImagesLow>,
    val images_web: List<Any>,
    val images_web_low: List<Any>,
    val in_wishlist: Int,
    val info: List<Info>,
    val is_custom_message: Int,
    val is_gc: Int,
    val is_new: Any,
    val is_sap: Int,
    val map_category: List<MapCategory>,
    val name: String,
    val on_discount: Int,
    val percent_off: Int,
    val product_id: Int,
    val product_slug: String,
    val product_type: Int,
    val products_description: String,
    val sale_price: Double,
    val shareable_link: String,
    val shipping_time: String,
    val short_description: String,
    val size_and_fit: Any,
    val size_and_fit_image: Any,
//    val suggested: List<Suggested>,
    val thumbnail_url: String,
    val vendor_name: String,
    val videos: List<Any>,
    val wrapping_methods: List<Any>
){

}

data class Attribute(
    val attribute_name: String,
    val options: List<Option>
)

data class AttributesOption(
    val name: String,
    val options: List<OptionX>
)

data class ExperienceInfo(
    val name: String,
    val text: String
)

data class ImagesLow(
    val high: String,
    val low: String
)

data class Info(
    val name: String,
    val text: String
)

data class MapCategory(
    val category_id: Int,
    val category_name: String
)

data class Suggested(
    val base_price: Double,
    val final_price_base: Double,
    val final_price_sale: Double,
    val in_wishlist: Int,
    val is_new: Boolean,
    val name: String,
    val product_id: Int,
    val product_slug: String,
    val sale_price: Double,
    val thumbnail_url: String
)

data class Option(
    val base_price: Double,
    val final_price_base: Double,
    val final_price_sale: Double,
    val images_low: List<Any>,
    val invId: Int,
    val invsku: String,
    val name: String,
    val on_discount: Int,
    val quantity: Int,
    val sale_price: Double,
    val sort_order: Int

)

data class OptionX(
    val name: String,
    val sort_order: Int
)