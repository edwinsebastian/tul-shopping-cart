package com.tul.shoppingcart.demo.repository

import com.tul.shoppingcart.demo.model.ShoppingCartProductsKey
import com.tul.shoppingcart.demo.model.ShoppingCartProductsModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShoppingCartProductRepository: CrudRepository<ShoppingCartProductsModel, ShoppingCartProductsKey>