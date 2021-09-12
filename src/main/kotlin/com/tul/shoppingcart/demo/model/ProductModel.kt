package com.tul.shoppingcart.demo.model

import java.util.*
import javax.persistence.*

@Entity
class ProductModel(
    @Id
    @GeneratedValue
    var id: UUID = UUID.randomUUID(),
    @Enumerated(EnumType.STRING)
    var status: ProductStatus = ProductStatus.ACTIVE,
    val name: String = "",
    val sku: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val discount: Boolean = false,
    val availableQuantity: Int = 0,
    val pendingQuantity: Int = 0,
    val finishedQuantity: Int = 0,
){
    val finalPrice
        get() = if (this.discount) this.price/2 else this.price

}

enum class ProductStatus(val state: String){
    ACTIVE("ACTIVE"),
    DELETED("DELETED")
}
