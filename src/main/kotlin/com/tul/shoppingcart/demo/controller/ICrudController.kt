package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.model.Model
import com.tul.shoppingcart.demo.service.ICrudService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

interface ICrudController<T> {
    var iCrudService: ICrudService<T>

    @PostMapping
    fun createResource(@RequestBody model: T): ResponseEntity<UUID>{
        return ResponseEntity.ok((iCrudService.createEntity(model) as Model).id)
    }

    @GetMapping("/{id}")
    fun readResource(@PathVariable id: UUID): ResponseEntity<T> {
        return ResponseEntity.ok(iCrudService.getEntity(id))
    }

    @GetMapping
    fun readResources(): ResponseEntity<List<T>> {
        return ResponseEntity.ok(iCrudService.getEntities())
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: UUID, @RequestBody model: T): ResponseEntity<UUID>{
        return ResponseEntity.ok((iCrudService.updateEntity(id, model) as Model).id)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: UUID): ResponseEntity<String> {
        ResponseEntity.ok(iCrudService.deleteEntity(id))

        return ResponseEntity.ok("Resource deleted")
    }

    @ExceptionHandler(*[NoSuchElementException::class])
    fun handleException(): ResponseEntity<String>{
        return ResponseEntity.badRequest().body("Resource not found")
    }
}