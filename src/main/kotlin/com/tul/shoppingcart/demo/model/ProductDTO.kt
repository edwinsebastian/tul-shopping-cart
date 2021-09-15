package com.tul.shoppingcart.demo.model

import java.util.UUID
import kotlin.streams.toList

data class ProductDTO(
    var productId: UUID,
    val name: String,
    val sku: String,
    val description: String,
    val discount: Boolean = false,
    val price: Double,
    val discountPrice: Double,
    var availableQuantity: Int,
    var pendingQuantity: Int,
    var completedQuantity: Int
) {
    constructor(productModel: ProductModel): this(
        productId = productModel.id,
        name = productModel.name,
        sku = productModel.sku,
        description = productModel.description,
        discount = productModel.discount,
        price = productModel.price,
        discountPrice = productModel.finalPrice,
        availableQuantity = productModel.availableQuantity,
        pendingQuantity = productModel.pendingQuantity,
        completedQuantity = productModel.completedQuantity
    )

    companion object {
        fun productsDto(productModelList: List<ProductModel>): List<ProductDTO>{
            return productModelList
                .parallelStream()
                .map { productModel ->
                    ProductDTO(
                        productId = productModel.id,
                        name = productModel.name,
                        sku = productModel.sku,
                        description = productModel.description,
                        discount = productModel.discount,
                        price = productModel.price,
                        discountPrice = productModel.finalPrice,
                        availableQuantity = productModel.availableQuantity,
                        pendingQuantity = productModel.pendingQuantity,
                        completedQuantity = productModel.completedQuantity
                    )
                }
                .toList()
        }
    }
}
