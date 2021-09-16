package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.model.ProductDTO
import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.service.ProductServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RestController
@RequestMapping("/v1/products")
class ProductController(
    override var crudServiceImpl: ProductServiceImpl
): ICrudController<ProductModel, UUID, ProductDTO>{

    override fun createResource(model: ProductModel): ResponseEntity<ProductDTO> {
        return ResponseEntity.ok(ProductDTO(crudServiceImpl.createEntity(model)))
    }

    override fun readResource(id: UUID): ResponseEntity<ProductDTO> {
        return ResponseEntity.ok(ProductDTO(crudServiceImpl.getEntity(id)))
    }

    override fun readResources(): ResponseEntity<List<ProductDTO>> {
        return ResponseEntity.ok(ProductDTO.productsDto(crudServiceImpl.getEntities()))
    }
}