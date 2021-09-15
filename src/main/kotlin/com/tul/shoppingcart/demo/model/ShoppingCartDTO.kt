package com.tul.shoppingcart.demo.model

import com.tul.shoppingcart.demo.enum.ShoppingCartStatus
import java.util.*
import java.util.stream.Collectors

data class ShoppingCartDTO(
    var shoppingCartId: UUID,
    var products: MutableList<ProductQuantityDTO>,
    var status: ShoppingCartStatus,
    var total: Double
){
    constructor(shoppingCartModel: ShoppingCartModel): this(
        shoppingCartId = shoppingCartModel.id,
        total = shoppingCartModel.totalCost,
        status = shoppingCartModel.status,
        products = shoppingCartModel.shoppingCartProductsModel
            .parallelStream()
            .map { ProductQuantityDTO(
                productId = it.productModel.id,
                quantity = it.quantity,
                unitPrice = it.productModel.price,
                discountPrice = it.productModel.finalPrice
            )}
            .collect(Collectors.toList())
    )
}