package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.enum.ShoppingCartStatusEnum
import com.tul.shoppingcart.demo.exception.ResourceNotFoundException
import com.tul.shoppingcart.demo.model.ShoppingCartModel
import com.tul.shoppingcart.demo.model.ShoppingCartProductsModel
import com.tul.shoppingcart.demo.repository.ShoppingCartRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import java.util.UUID

@Service
class ShoppingCartServiceImpl(
    override var crudRepositoryImpl: ShoppingCartRepository
): ICrudService<ShoppingCartModel, UUID> {

    override fun getEntity(id: UUID): ShoppingCartModel {
        try {
            return crudRepositoryImpl.findById(id).get()
        }catch (ex:NoSuchElementException){
            throw ResourceNotFoundException("ShoppingCart with id:$id not found.")
        }
    }

    override fun updateEntity(id: UUID, model: ShoppingCartModel): ShoppingCartModel {
        if(!crudRepositoryImpl.existsById(id)){
            throw ResourceNotFoundException("Shopping cart with id:$id not found.")
        }
        model.id = id

        return crudRepositoryImpl.save(model)
    }

    fun updateShoppingCartTotal(id: UUID, costVariation: Double): ShoppingCartModel {
        val shoppingCartModel: ShoppingCartModel = getEntity(id)
        ShoppingCartModel(
            shoppingCartProductsModel = mutableSetOf<ShoppingCartProductsModel>(),
            status = ShoppingCartStatusEnum.PENDING,
            totalCost = 1000.0
        )
        shoppingCartModel.totalCost += costVariation
        return crudRepositoryImpl.save(shoppingCartModel)
    }

    fun shoppingCartCheckout(id: UUID): ShoppingCartModel{
        val shoppingCartModel: ShoppingCartModel = getEntity(id)
        shoppingCartModel.status = ShoppingCartStatusEnum.COMPLETED
        return crudRepositoryImpl.save(shoppingCartModel)
    }
}