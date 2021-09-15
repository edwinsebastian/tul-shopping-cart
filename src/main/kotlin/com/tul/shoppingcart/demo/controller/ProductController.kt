package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.model.ProductDTO
import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.service.ICrudService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RestController
@RequestMapping("v1/products")
class ProductController(
    override var iCrudService: ICrudService<ProductModel, UUID>
): ICrudController<ProductModel, UUID, ProductDTO>{

    override fun createResource(model: ProductModel): ResponseEntity<ProductDTO> {
        return ResponseEntity.ok(ProductDTO(iCrudService.createEntity(model)))
    }

    override fun readResource(id: UUID): ResponseEntity<ProductDTO> {
        return ResponseEntity.ok(ProductDTO(iCrudService.getEntity(id)))
    }

    override fun readResources(): ResponseEntity<List<ProductDTO>> {
        return ResponseEntity.ok(ProductDTO.productsDto(iCrudService.getEntities()))
    }

    override fun updateProduct(id: UUID, model: ProductModel): ResponseEntity<UUID> {
        return ResponseEntity.ok(iCrudService.updateEntity(id, model).id)
    }
}