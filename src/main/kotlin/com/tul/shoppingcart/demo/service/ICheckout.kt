package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.model.ShoppingCartModel

interface ICheckout<T> {
    fun checkout(model: T): Double
}