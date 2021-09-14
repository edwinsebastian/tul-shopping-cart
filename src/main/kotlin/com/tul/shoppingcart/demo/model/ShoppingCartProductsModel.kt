package com.tul.shoppingcart.demo.model

import javax.persistence.Entity
import javax.persistence.EmbeddedId
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.JoinColumn

@Entity
class ShoppingCartProductsModel(
    @EmbeddedId
    var id: ShoppingCartProductsKey = ShoppingCartProductsKey(),
    @ManyToOne
    @MapsId("shoppingCartModelId")
    @JoinColumn(name = "shopping_cart_model_id")
    var shoppingCartModel: ShoppingCartModel = ShoppingCartModel(),
    @ManyToOne
    @MapsId("productModelId")
    @JoinColumn(name = "product_model_id")
    var productModel: ProductModel = ProductModel(),
    var quantity: Int = 0
)