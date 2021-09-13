package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.model.ShoppingCartModel
import com.tul.shoppingcart.demo.repository.ShoppingCartRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ShoppingCartServiceImpl(
    private var shoppingCartRepository: ShoppingCartRepository,
    override var crudRepository: CrudRepository<ShoppingCartModel, UUID>
): IShoppingCartService<ShoppingCartModel> {
    override fun checkout(model: ShoppingCartModel): Double {
        TODO("Not yet implemented")
    }

    override fun addItem(cartId: UUID, itemId: UUID): ShoppingCartModel {
        TODO("Not yet implemented")
    }

    override fun removeItem(cartId: UUID, itemId: UUID): ShoppingCartModel {
        TODO("Not yet implemented")
    }


}