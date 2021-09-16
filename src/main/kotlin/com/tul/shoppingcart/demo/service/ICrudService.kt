package com.tul.shoppingcart.demo.service

import org.springframework.data.repository.CrudRepository

interface ICrudService<T, ID> {
    val crudRepositoryImpl: CrudRepository<T, ID>

    fun getEntities() = crudRepositoryImpl.findAll().toList()

    fun getEntity(id: ID) = crudRepositoryImpl.findById(id).get()

    fun createEntity(model: T) = crudRepositoryImpl.save(model)

    fun updateEntity(id: ID, model: T): T

    fun deleteEntity(id: ID) = crudRepositoryImpl.deleteById(id)
}