package com.tul.shoppingcart.demo.repository

import com.tul.shoppingcart.demo.model.ShoppingCartModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ShoppingCartRepository: CrudRepository<ShoppingCartModel, UUID>