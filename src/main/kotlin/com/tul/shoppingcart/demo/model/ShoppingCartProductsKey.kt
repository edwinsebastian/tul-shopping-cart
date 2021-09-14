package com.tul.shoppingcart.demo.model

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class ShoppingCartProductsKey(
    @Column(name = "shopping_cart_model_id")
    val shoppingCartModelId: UUID = UUID.randomUUID(),
    @Column(name = "product_model_id")
    val productModelId: UUID = UUID.randomUUID()
): Serializable