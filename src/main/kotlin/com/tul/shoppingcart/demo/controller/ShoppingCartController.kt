package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.model.*
import com.tul.shoppingcart.demo.service.ICrudService
import com.tul.shoppingcart.demo.service.IShoppingCartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("v1/shoppingcart")
class ShoppingCartController(
    private val iShoppingCartService: IShoppingCartService<ShoppingCartProductsModel, ShoppingCartProductsKey>,
    override var iCrudService: ICrudService<ShoppingCartModel, UUID>
): ICrudController<ShoppingCartModel, UUID>{

    override fun updateProduct(id: UUID, model: ShoppingCartModel): ResponseEntity<UUID> {
        return ResponseEntity.ok(iCrudService.updateEntity(id, model).id)
    }

    @PutMapping("/{shoppingCartId}/product")
    fun modifyProductQty(@PathVariable shoppingCartId: UUID, @RequestBody productQuantityDTO: ProductQuantityDTO): ResponseEntity<ShoppingCartDTO>{
        return ResponseEntity.ok(iShoppingCartService.modifyProductQty(shoppingCartId, productQuantityDTO.productId, productQuantityDTO.quantity))
    }

    @PutMapping("/{shoppingCartId}/checkout")
    fun shoppingCartCheckout(@PathVariable shoppingCartId: UUID): ResponseEntity<ShoppingCartDTO> {
        return ResponseEntity.ok(iShoppingCartService.checkout(shoppingCartId))
    }
}