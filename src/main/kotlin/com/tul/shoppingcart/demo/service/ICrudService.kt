package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.model.Model
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ICrudService<T> {
    var crudRepository: CrudRepository<T, UUID>

    fun getEntities() = crudRepository.findAll().toList()

    fun getEntity(id: UUID) = crudRepository.findById(id).get()

    fun createEntity(model: T) = crudRepository.save(model)

    fun updateEntity(id: UUID, model: T): T{
        if(!crudRepository.existsById(id)){
            throw NoSuchElementException()
        }
        (model as Model).id = id

        return crudRepository.save(model as T)
    }

    fun deleteEntity(id: UUID) = crudRepository.deleteById(id)
}