package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.exception.OutStockEx
import com.tul.shoppingcart.demo.enum.ProductStatus
import com.tul.shoppingcart.demo.exception.ResourceNotFoundEx
import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.model.ShoppingCartDTO
import com.tul.shoppingcart.demo.repository.ProductRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.stream.Collectors

@Service
class ProductServiceImpl(
    private var productRepository: ProductRepository,
    override var crudRepository: CrudRepository<ProductModel, UUID>
): ICrudService<ProductModel, UUID> {
    override fun getEntities(): List<ProductModel> {
        return productRepository.findAllByStatusEquals(ProductStatus.ACTIVE).toList()
    }

    override fun getEntity(id: UUID): ProductModel {
        try {
            return productRepository.findByIdAndStatusEquals(id, ProductStatus.ACTIVE).get()
        }catch (ex: java.util.NoSuchElementException){
            throw ResourceNotFoundEx("Product with id:$id not found.")
        }
    }

    override fun deleteEntity(id: UUID) {
        val productModel: ProductModel = getEntity(id)
        productModel.status = ProductStatus.DELETED
        crudRepository.save(productModel)
    }

    override fun updateEntity(id: UUID, model: ProductModel): ProductModel {
        if(!crudRepository.existsById(id)){
            throw NoSuchElementException()
        }
        model.id = id

        return crudRepository.save(model)
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

        return crudRepository.save(productModel)
    }

    fun moveStockPendingToCompleted(shoppingCartDTO: ShoppingCartDTO): MutableIterable<ProductModel> {
        val productsQuantity: Map<UUID, Int> =
            shoppingCartDTO.products
                .map { it.productId to it.quantity }
                .toMap()

        val productModelList: List<ProductModel> = crudRepository.findAllById(
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

        return crudRepository.saveAll(productModelList)
    }
}