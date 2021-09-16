package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.exception.MethodNotAllowedException
import com.tul.shoppingcart.demo.model.*
import com.tul.shoppingcart.demo.service.ShoppingCartProductServiceImpl
import com.tul.shoppingcart.demo.service.ShoppingCartServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/v1/shoppingcart")
class ShoppingCartController(
    private var cartService: ShoppingCartProductServiceImpl,
    override var crudServiceImpl: ShoppingCartServiceImpl
): ICrudController<ShoppingCartModel, UUID, ShoppingCartDTO>{

    override fun createResource(model: ShoppingCartModel): ResponseEntity<ShoppingCartDTO> {
        return ResponseEntity.ok(ShoppingCartDTO(crudServiceImpl.createEntity(model)))
    }

    override fun readResource(id: UUID): ResponseEntity<ShoppingCartDTO> {
        return ResponseEntity.ok(ShoppingCartDTO(crudServiceImpl.getEntity(id)))
    }

    override fun readResources(): ResponseEntity<List<ShoppingCartDTO>> {
        return ResponseEntity.ok(ShoppingCartDTO.shoppingCartsDTO(crudServiceImpl.getEntities()))
    }

    override fun updateResource(id: UUID, model: ShoppingCartModel): ResponseEntity<ResourceUpdatedDTO> {
        throw MethodNotAllowedException("Is not allowed modify the shopping cart.")
    }

    @PutMapping("/{shoppingCartId}/product")
    fun modifyCartItems(@PathVariable shoppingCartId: UUID, @RequestBody productQuantityDTO: ProductQuantityDTO): ResponseEntity<ShoppingCartDTO>{
        return ResponseEntity.ok(cartService.modifyCartItems(shoppingCartId, productQuantityDTO.productId, productQuantityDTO.quantity))
    }

    @PutMapping("/{shoppingCartId}/checkout")
    fun shoppingCartCheckout(@PathVariable shoppingCartId: UUID): ResponseEntity<ShoppingCartDTO> {
        return ResponseEntity.ok(cartService.checkout(shoppingCartId))
    }
}
