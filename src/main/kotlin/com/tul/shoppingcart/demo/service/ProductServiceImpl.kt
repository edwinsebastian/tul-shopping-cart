package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.exception.OutStockEx
import com.tul.shoppingcart.demo.enum.ProductStatusEnum
import com.tul.shoppingcart.demo.exception.ResourceNotFoundException
import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.model.ShoppingCartDTO
import com.tul.shoppingcart.demo.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.stream.Collectors

@Service
class ProductServiceImpl(
    override var crudRepositoryImpl: ProductRepository,
): ICrudService<ProductModel, UUID> {
    override fun getEntities(): List<ProductModel> {
        return crudRepositoryImpl.findAllByStatusEquals(ProductStatusEnum.ACTIVE).toList()
    }

    override fun getEntity(id: UUID): ProductModel {
        try {
            return crudRepositoryImpl.findByIdAndStatusEquals(id, ProductStatusEnum.ACTIVE).get()
        }catch (ex: java.util.NoSuchElementException){
            throw ResourceNotFoundException("Product with id:$id not found.")
        }
    }

    override fun deleteEntity(id: UUID) {
        val productModel: ProductModel = getEntity(id)
        productModel.status = ProductStatusEnum.DELETED
        crudRepositoryImpl.save(productModel)
    }

    override fun updateEntity(id: UUID, model: ProductModel): ProductModel {
        if(!crudRepositoryImpl.existsById(id)){
            throw ResourceNotFoundException("Product with id:$id not found.")
        }
        model.id = id

        return crudRepositoryImpl.save(model)
    }

    fun getProductPrice(id: UUID) = getEntity(id).finalPrice

    fun calculateProductsPrice(id: UUID, quantity: Int) = this.getProductPrice(id) * quantity

    fun validateStockAvailability(productModel: ProductModel, quantity: Int): Boolean {
        if(quantity <= productModel.availableQuantity){
            return true
        }
        throw OutStockEx("Not enough stock for product: ${productModel.id}.")
    }

    fun moveStock(id: UUID, quantity: Int): ProductModel {
        val productModel: ProductModel = getEntity(id)
        this.validateStockAvailability(productModel, quantity)
        productModel.availableQuantity -= quantity
        productModel.pendingQuantity += quantity

        return crudRepositoryImpl.save(productModel)
    }

    fun moveStockPendingToCompleted(shoppingCartDTO: ShoppingCartDTO): MutableIterable<ProductModel> {
        val productsQuantity: Map<UUID, Int> =
            shoppingCartDTO.products
                .map { it.productId to it.quantity }
                .toMap()

        val productModelList: List<ProductModel> = crudRepositoryImpl.findAllById(
            shoppingCartDTO.products
                .parallelStream()
                .map { it.productId }
                .collect(Collectors.toList())
            .asIterable()
        ).toList()

        productModelList
            .parallelStream()
            .forEach {
                val quantity: Int = productsQuantity.getOrDefault(it.id, 0)
                it.pendingQuantity -= quantity
                it.completedQuantity += quantity
            }

        return crudRepositoryImpl.saveAll(productModelList)
    }
}