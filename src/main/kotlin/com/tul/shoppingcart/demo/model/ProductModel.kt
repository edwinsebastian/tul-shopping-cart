package com.tul.shoppingcart.demo.model

import javax.persistence.*

@Entity(name = "ProductModel")
class ProductModel(
    @Enumerated(EnumType.STRING)
    var status: ProductStatus = ProductStatus.ACTIVE,
    val name: String = "",
    val sku: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val discount: Boolean = false,
    var availableQuantity: Int = 0,
    var pendingQuantity: Int = 0,
    var completedQuantity: Int = 0
): Model() {
    val finalPrice
        get() = if (this.discount) this.price/2 else this.price
}

enum class ProductStatus(val status: String){
    ACTIVE("ACTIVE"),
    DELETED("DELETED")
}
