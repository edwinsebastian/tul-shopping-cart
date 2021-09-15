package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.model.ShoppingCartDTO
import com.tul.shoppingcart.demo.model.ShoppingCartModel
import java.util.*

interface IShoppingCartService<T, ID>: ICrudService<T, ID>, ICheckout{

    fun modifyProductQty(cartId: UUID, productId: UUID, quantity: Int): ShoppingCartDTO
}