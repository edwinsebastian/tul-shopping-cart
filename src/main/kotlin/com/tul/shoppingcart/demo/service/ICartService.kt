package com.tul.shoppingcart.demo.service

import java.util.UUID

interface ICartService<T, ID, R>: ICrudService<T, ID>, ICheckout<R>{

    fun modifyCartItems(cartId: UUID, productId: UUID, quantity: Int): R
}