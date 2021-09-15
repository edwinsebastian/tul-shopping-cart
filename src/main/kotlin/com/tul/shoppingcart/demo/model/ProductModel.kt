package com.tul.shoppingcart.demo.model

import com.tul.shoppingcart.demo.enum.ProductStatus
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.EnumType
import javax.persistence.OneToMany

@Entity
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
    var completedQuantity: Int = 0,
    @OneToMany(mappedBy = "productModel")
    var shoppingCartProductsModel: MutableSet<ShoppingCartProductsModel> = mutableSetOf(),

    ): Model() {
    val finalPrice
        get() = if (this.discount) this.price/2 else this.price
}