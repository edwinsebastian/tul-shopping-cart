package com.tul.shoppingcart.demo.model

import javax.persistence.*

@Entity
class ShoppingCartModel(
    @ManyToMany
    var products: MutableList<ProductModel> = mutableListOf(),
    @Enumerated(EnumType.STRING)
    var status: ShoppingCartStatus = ShoppingCartStatus.PENDING,
    var totalCost: Double = 0.0
):Model()

enum class ShoppingCartStatus(val status: String){
    PENDING("PENDING"),
    COMPLETED("COMPLETED")
}