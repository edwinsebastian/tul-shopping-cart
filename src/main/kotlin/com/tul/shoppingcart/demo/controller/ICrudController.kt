package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.exception.OutStockEx
import com.tul.shoppingcart.demo.exception.ResourceNotFoundEx
import com.tul.shoppingcart.demo.service.ICrudService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

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

    @ExceptionHandler(value = [
        IllegalArgumentException::class,
        MethodArgumentTypeMismatchException::class,
        NoSuchElementException::class,
        OutStockEx::class,
        ResourceNotFoundEx::class,
        EmptyResultDataAccessException::class
    ])
    fun handleException(ex: Exception): ResponseEntity<String>{
        return ResponseEntity.badRequest().body("Error found, can not proceed. ${ex.message}")
    }
}