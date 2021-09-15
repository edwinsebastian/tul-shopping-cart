package com.tul.shoppingcart.demo.service

import com.tul.shoppingcart.demo.enum.ProductStatus
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
        return productRepository.findByIdAndStatusEquals(id, ProductStatus.ACTIVE).get()
    }

    override fun deleteEntity(id: UUID) {
        val productModel: ProductModel = crudRepository.findById(id).get()
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

    fun getProductPrice(id: UUID) = crudRepository.findById(id).get().finalPrice

    fun calculateProductsPrice(id: UUID, quantity: Int) = this.getProductPrice(id) * quantity

    fun validateStockAvailability(productModel: ProductModel, quantity: Int): Boolean {
        if(quantity <= productModel.availableQuantity){
            return true
        }
        throw Exception("Not enough stock for product: ${productModel.id}. Available: ${productModel.availableQuantity} Required: $quantity")
    }

    fun moveStock(id: UUID, quantity: Int): ProductModel {
        val productModel: ProductModel = crudRepository.findById(id).get()
        this.validateStockAvailability(productModel, quantity)
        productModel.availableQuantity -= quantity
        productModel.pendingQuantity += quantity
        return crudRepository.save(productModel)
    }

    fun moveStockPendingToCompleted(shoppingCartDTO: ShoppingCartDTO){
        val products: MutableList<Map<UUID, Int>> = shoppingCartDTO.products
            .parallelStream()
            .map { mapOf(it.productId to it.quantity) }
            .collect(Collectors.toList())

        val productModel: List<ProductModel> = crudRepository.findAllById(
            shoppingCartDTO.products
                .parallelStream()
                .map { it.productId}
                .collect(Collectors.toList())
            .asIterable()
        ).toList()



//        productModel.pendingQuantity -= quantity
//        productModel.completedQuantity += quantity
//        crudRepository.save(productModel)
    }
}