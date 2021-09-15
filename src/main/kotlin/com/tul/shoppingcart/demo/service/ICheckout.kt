package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.model.ShoppingCartDTO
import java.util.UUID

interface ICheckout {
    fun checkout(cartId: UUID): ShoppingCartDTO
}