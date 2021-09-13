package com.tul.shoppingcart.demo.service

import java.util.*

interface IShoppingCartService<T>: ICrudService<T>, ICheckout<T>{
    fun addItem(cartId: UUID, itemId: UUID): T

    fun removeItem(cartId: UUID, itemId: UUID): T
}