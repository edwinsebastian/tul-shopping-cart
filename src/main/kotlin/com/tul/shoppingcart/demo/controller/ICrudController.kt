package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.enum.EntityStatusEnum
import com.tul.shoppingcart.demo.model.Model
import com.tul.shoppingcart.demo.model.ResourceUpdatedDTO
import com.tul.shoppingcart.demo.service.ICrudService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface ICrudController<T, ID, R> {
    val crudServiceImpl: ICrudService<T, ID>

    @PostMapping
    fun createResource(@RequestBody model: T): ResponseEntity<R>

    @GetMapping("/{id}")
    fun readResource(@PathVariable id: ID): ResponseEntity<R>

    @GetMapping
    fun readResources(): ResponseEntity<List<R>>

    @PutMapping("/{id}")
    fun updateResource(@PathVariable id: ID, @RequestBody model: T): ResponseEntity<ResourceUpdatedDTO>{
        return ResponseEntity.ok(ResourceUpdatedDTO((crudServiceImpl.updateEntity(id, model) as Model).id, EntityStatusEnum.UPDATED))
    }

    @DeleteMapping("/{id}")
    fun deleteResource(@PathVariable id: ID): ResponseEntity<ResourceUpdatedDTO> {
        return ResponseEntity.ok(ResourceUpdatedDTO((crudServiceImpl.deleteEntity(id) as Model).id, EntityStatusEnum.DELETED))
    }
}