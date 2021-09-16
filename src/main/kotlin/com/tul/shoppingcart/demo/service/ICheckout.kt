package com.tul.shoppingcart.demo.service

import java.util.UUID

interface ICheckout<R> {
    fun checkout(cartId: UUID): R
}