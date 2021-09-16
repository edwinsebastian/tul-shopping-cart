package com.tul.shoppingcart.demo.repository

import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.enum.ProductStatusEnum
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.Optional

@Repository
interface ProductRepository: CrudRepository<ProductModel, UUID> {

    fun findAllByStatusEquals(status: ProductStatusEnum): Iterable<ProductModel>

    fun findByIdAndStatusEquals(id: UUID, status: ProductStatusEnum): Optional<ProductModel>
}