package com.tul.shoppingcart.demo.model

import com.tul.shoppingcart.demo.enum.ShoppingCartStatusEnum
import java.util.UUID
import kotlin.streams.toList

data class ShoppingCartDTO(
    var shoppingCartId: UUID,
    var products: List<ProductQuantityDTO>,
    var status: ShoppingCartStatusEnum,
    var total: Double
){
     constructor(shoppingCartModel: ShoppingCartModel): this(
        shoppingCartId = shoppingCartModel.id,
        total = shoppingCartModel.totalCost,
        status = shoppingCartModel.status,
        products = productsMapper(shoppingCartModel)
    )

    companion object {
        fun productsMapper(shoppingCartModel: ShoppingCartModel): List<ProductQuantityDTO> {
            if(shoppingCartModel.shoppingCartProductsModel.isEmpty()){
                return listOf()
            }
            return shoppingCartModel.shoppingCartProductsModel
                .parallelStream()
                .map {
                    ProductQuantityDTO(
                        productId = it.productModel.id,
                        quantity = it.quantity,
                        unitPrice = it.productModel.price,
                        discountPrice = it.productModel.finalPrice
                    )
                }
                .toList()
        }

        fun shoppingCartsDTO(shoppingCartModelList: List<ShoppingCartModel>): List<ShoppingCartDTO>{
            return shoppingCartModelList
                .parallelStream()
                .map { shoppingCartModel ->
                    ShoppingCartDTO(
                        shoppingCartId = shoppingCartModel.id,
                        total = shoppingCartModel.totalCost,
                        status = shoppingCartModel.status,
                        products = productsMapper(shoppingCartModel)
                    )
                }
                .toList()
        }
    }
}