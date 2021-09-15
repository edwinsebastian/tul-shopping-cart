package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.model.ShoppingCartDTO
import com.tul.shoppingcart.demo.model.ShoppingCartModel
import com.tul.shoppingcart.demo.model.ShoppingCartProductsKey
import com.tul.shoppingcart.demo.model.ShoppingCartProductsModel
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import java.util.UUID
import java.util.stream.Collectors

@Service
class ShoppingCartProductServiceImpl(
    private var shoppingCartServiceImpl: ShoppingCartServiceImpl,
    private var productServiceImpl: ProductServiceImpl,
    override var crudRepository: CrudRepository<ShoppingCartProductsModel, ShoppingCartProductsKey>
): IShoppingCartService<ShoppingCartProductsModel, ShoppingCartProductsKey>{

    override fun updateEntity(id: ShoppingCartProductsKey, model: ShoppingCartProductsModel): ShoppingCartProductsModel {
        if(!crudRepository.existsById(id)){
            throw NoSuchElementException()
        }
        model.id = id
        return crudRepository.save(model)
    }

    override fun modifyProductQty(cartId: UUID, productId: UUID, quantity: Int): ShoppingCartDTO {
        val shoppingCartProductsKey = ShoppingCartProductsKey(shoppingCartModelId = cartId, productModelId = productId)
        var shoppingCartProductsModel: ShoppingCartProductsModel? = crudRepository.findByIdOrNull(shoppingCartProductsKey)
        var shoppingCartModel: ShoppingCartModel
        if(shoppingCartProductsModel != null){
            if(quantity == 0){
                val stockQuantityToRelease: Int = -1*shoppingCartProductsModel.quantity
                productServiceImpl.moveStock(productId, stockQuantityToRelease)
                crudRepository.delete(shoppingCartProductsModel)

                shoppingCartModel = shoppingCartServiceImpl.updateShoppingCartTotal(cartId, productServiceImpl.calculateProductsPrice(productId, stockQuantityToRelease))
            }else{
                val quantityVariation: Int = quantity - shoppingCartProductsModel.quantity
                val costVariation: Double = productServiceImpl.calculateProductsPrice(productId, quantityVariation)

                productServiceImpl.moveStock(productId, quantityVariation)
                this.updateEntity(shoppingCartProductsKey, shoppingCartProductsModel.also { it.quantity += quantityVariation })

                shoppingCartModel = shoppingCartServiceImpl.updateShoppingCartTotal(cartId, costVariation)
            }
        }else{
            shoppingCartModel = shoppingCartServiceImpl.updateShoppingCartTotal(cartId, productServiceImpl.calculateProductsPrice(productId, quantity))
            this.createEntity(
                ShoppingCartProductsModel(
                    id = shoppingCartProductsKey,
                    shoppingCartModel = shoppingCartModel,
                    productModel = productServiceImpl.moveStock(productId, quantity),
                    quantity = quantity
                )
            )
        }

        return ShoppingCartDTO(shoppingCartModel)
    }

    override fun checkout(cartId: UUID): ShoppingCartDTO {
        val shoppingCartDTO = ShoppingCartDTO(shoppingCartServiceImpl.shoppingCartCheckout(cartId))

        productServiceImpl.moveStockPendingToCompleted(shoppingCartDTO)

        return shoppingCartDTO
    }
}