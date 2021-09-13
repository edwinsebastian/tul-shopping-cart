package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.model.ProductStatus
import com.tul.shoppingcart.demo.repository.ProductRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductServiceImpl(
    private var productRepository: ProductRepository,
    override var crudRepository: CrudRepository<ProductModel, UUID>
): IServiceCrud<ProductModel> {
    override fun getEntities(): List<ProductModel> {
        return productRepository.findAllByStatusEquals(ProductStatus.ACTIVE).toList()
    }

    override fun getEntity(id: UUID): ProductModel {
        return productRepository.findByIdAndStatusEquals(id, ProductStatus.ACTIVE).get()
    }

//    override fun createEntity(productModel: ProductModel): ProductModel {
//        return crudRepository.save(productModel)
//    }
//
//    override fun updateEntity(id: UUID, productModel: ProductModel): UUID {
//        if(!crudRepository.existsById(id)){
//            throw NoSuchElementException()
//        }
//        productModel.id = id
//        return crudRepository.save(productModel).id
//    }

    override fun deleteEntity(id: UUID) {
        val productModel: ProductModel = crudRepository.findById(id).get()
        productModel.status = ProductStatus.DELETED
        crudRepository.save(productModel)
    }
}