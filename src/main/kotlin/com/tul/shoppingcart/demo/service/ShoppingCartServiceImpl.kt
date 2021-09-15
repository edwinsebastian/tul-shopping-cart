package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.enum.ShoppingCartStatus
import com.tul.shoppingcart.demo.exception.ResourceNotFoundEx
import com.tul.shoppingcart.demo.model.ShoppingCartModel
import com.tul.shoppingcart.demo.repository.ShoppingCartRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import java.util.UUID

@Service
class ShoppingCartServiceImpl(
    private val shoppingCartRepository: ShoppingCartRepository,
    override var crudRepository: CrudRepository<ShoppingCartModel, UUID>
): ICrudService<ShoppingCartModel, UUID> {

    override fun getEntity(id: UUID): ShoppingCartModel {
        try {
            return crudRepository.findById(id).get()
        }catch (ex:NoSuchElementException){
            throw ResourceNotFoundEx("ShoppingCart with id:$id not found.")
        }
    }

    override fun updateEntity(id: UUID, model: ShoppingCartModel): ShoppingCartModel {
        if(!crudRepository.existsById(id)){
            throw ResourceNotFoundEx("Shopping cart with id:$id not found.")
        }
        model.id = id

        return crudRepository.save(model)
    }

    fun updateShoppingCartTotal(id: UUID, costVariation: Double): ShoppingCartModel {
        val shoppingCartModel: ShoppingCartModel = getEntity(id)
        shoppingCartModel.totalCost += costVariation
        return crudRepository.save(shoppingCartModel)
    }

    fun shoppingCartCheckout(id: UUID): ShoppingCartModel{
        val shoppingCartModel: ShoppingCartModel = getEntity(id)
        shoppingCartModel.status = ShoppingCartStatus.COMPLETED
        return crudRepository.save(shoppingCartModel)
    }
}