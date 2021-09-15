package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.model.Model
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ICrudService<T, ID> {
    var crudRepository: CrudRepository<T, ID>

    fun getEntities() = crudRepository.findAll().toList()

    fun getEntity(id: ID) = crudRepository.findById(id).get()

    fun createEntity(model: T) = crudRepository.save(model)

    fun updateEntity(id: ID, model: T): T

    fun deleteEntity(id: ID) = crudRepository.deleteById(id)
}