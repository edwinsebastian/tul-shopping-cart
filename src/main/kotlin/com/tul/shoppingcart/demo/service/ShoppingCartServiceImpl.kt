package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.enum.ShoppingCartStatus
import com.tul.shoppingcart.demo.model.ShoppingCartModel
import com.tul.shoppingcart.demo.repository.ShoppingCartRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import java.util.UUID

@Service
class ShoppingCartServiceImpl(
    override var crudRepository: CrudRepository<ShoppingCartModel, UUID>
): ICrudService<ShoppingCartModel, UUID> {

    override fun updateEntity(id: UUID, model: ShoppingCartModel): ShoppingCartModel {
        if(!crudRepository.existsById(id)){
            throw NoSuchElementException()
        }
        model.id = id

        return crudRepository.save(model)
    }

    fun updateShoppingCartTotal(id: UUID, costVariation: Double): ShoppingCartModel {
        val shoppingCartModel: ShoppingCartModel = crudRepository.findById(id).get()
        shoppingCartModel.totalCost += costVariation
        return crudRepository.save(shoppingCartModel)
    }

    fun shoppingCartCheckout(id: UUID): ShoppingCartModel{
        val shoppingCartModel: ShoppingCartModel = crudRepository.findById(id).get()
        shoppingCartModel.status = ShoppingCartStatus.COMPLETED
        return crudRepository.save(shoppingCartModel)
    }
}