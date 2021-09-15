package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.service.ICrudService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface ICrudController<T, ID, R> {
    var iCrudService: ICrudService<T, ID>

    @PostMapping
    fun createResource(@RequestBody model: T): ResponseEntity<R>

    @GetMapping("/{id}")
    fun readResource(@PathVariable id: ID): ResponseEntity<R>

    @GetMapping
    fun readResources(): ResponseEntity<List<R>>

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: ID, @RequestBody model: T): ResponseEntity<ID>

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: ID): ResponseEntity<String> {
        ResponseEntity.ok(iCrudService.deleteEntity(id))

        return ResponseEntity.ok("Resource deleted")
    }

    @ExceptionHandler(*[NoSuchElementException::class])
    fun handleException(): ResponseEntity<String>{
        return ResponseEntity.badRequest().body("Resource not found")
    }

//    todo custom exception para cantidades no disponibles
}