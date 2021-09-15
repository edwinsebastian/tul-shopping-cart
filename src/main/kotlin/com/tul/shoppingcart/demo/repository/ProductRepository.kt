package com.tul.shoppingcart.demo.repository

import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.enum.ProductStatus
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.Optional

@Repository
interface ProductRepository: CrudRepository<ProductModel, UUID> {

    fun findAllByStatusEquals(status: ProductStatus): Iterable<ProductModel>

    fun findByIdAndStatusEquals(id: UUID, status: ProductStatus): Optional<ProductModel>
}