package com.tul.shoppingcart.demo.model

import java.util.UUID

data class ProductQuantityDTO(
    val productId: UUID,
    val quantity: Int,
    val unitPrice: Double?,
    val discountPrice: Double?
)