package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.service.IServiceCrud
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/products")
class ProductController(
    private val iServiceCrud: IServiceCrud<ProductModel>
) {

    @GetMapping("")
    fun getActiveProducts(): ResponseEntity<List<ProductModel>> {
        return ResponseEntity.ok(iServiceCrud.getEntities())
    }

    @GetMapping("/{id}")
    fun getActiveProductById(@PathVariable id: UUID): ResponseEntity<ProductModel> {
        return ResponseEntity.ok(iServiceCrud.getEntity(id))
    }

    @PostMapping("")
    fun createProduct(@RequestBody productModel: ProductModel): ResponseEntity<UUID> {
        return ResponseEntity.ok(iServiceCrud.createEntity(productModel))
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: UUID, @RequestBody productModel: ProductModel): ResponseEntity<UUID>{
        return ResponseEntity.ok(iServiceCrud.updateEntity(id, productModel))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: UUID): ResponseEntity<String> {
        ResponseEntity.ok(iServiceCrud.deleteEntity(id))

        return ResponseEntity.ok("Product deleted")
    }

    @ExceptionHandler(*[NoSuchElementException::class])
    fun handleException(): ResponseEntity<String>{
        return ResponseEntity.badRequest().body("Product not found")
    }
}