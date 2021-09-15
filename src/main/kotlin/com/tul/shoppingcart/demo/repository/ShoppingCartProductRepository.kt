package com.tul.shoppingcart.demo.repository

import com.tul.shoppingcart.demo.model.ShoppingCartProductsKey
import com.tul.shoppingcart.demo.model.ShoppingCartProductsModel
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ShoppingCartProductRepository: CrudRepository<ShoppingCartProductsModel, ShoppingCartProductsKey> {
}