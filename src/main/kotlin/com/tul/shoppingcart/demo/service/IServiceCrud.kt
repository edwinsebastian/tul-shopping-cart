package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.model.ProductModel
import java.util.*

interface IServiceCrud<T> {
    fun getEntities(): List<T>
    fun getEntity(id: UUID): T
    fun createEntity(model: T): UUID
    fun updateEntity(id: UUID, model: T): UUID
    fun deleteEntity(id: UUID)
}