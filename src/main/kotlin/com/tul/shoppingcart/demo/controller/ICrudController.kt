package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.service.ICrudService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface ICrudController<T, ID> {
    var iCrudService: ICrudService<T, ID>

    @PostMapping
    fun createResource(@RequestBody model: T): ResponseEntity<T>{
        return ResponseEntity.ok(iCrudService.createEntity(model))
    }

    @GetMapping("/{id}")
    fun readResource(@PathVariable id: ID): ResponseEntity<T> {
        return ResponseEntity.ok(iCrudService.getEntity(id))
    }

    @GetMapping
    fun readResources(): ResponseEntity<List<T>> {
        return ResponseEntity.ok(iCrudService.getEntities())
    }

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