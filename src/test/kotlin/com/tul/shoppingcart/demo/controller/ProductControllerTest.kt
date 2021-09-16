package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.enum.EntityStatusEnum
import com.tul.shoppingcart.demo.model.ProductDTO
import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.model.ResourceUpdatedDTO
import com.tul.shoppingcart.demo.service.ProductServiceImpl
import org.junit.Assert
import org.junit.jupiter.api.Test

import org.mockito.kotlin.any

import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity

class ProductControllerTest {
    @Test
    fun createResource() {
        /* Given */
        var productModel = ProductModel(
            name = "Steel",
            sku = "XXX111",
            description = "new product",
            price = 10000.00,
            discount = true,
            availableQuantity = 10,
            pendingQuantity = 0,
            completedQuantity = 0
        )
        var productDTO = ProductDTO(productModel)
        val serviceMock = mock<ProductServiceImpl> {
            on { createEntity(any()) } doReturn productModel
        }
        val controller = ProductController(serviceMock)

        /* When */
        val httpResponse: ResponseEntity<ProductDTO> = controller.createResource(productModel)

        /* Then */
        Assert.assertEquals(httpResponse.statusCode, HttpStatus.OK)
        Assert.assertEquals(httpResponse.body?.productId, productDTO.productId)
        Assert.assertEquals(httpResponse.body?.name, productDTO.name)
        Assert.assertEquals(httpResponse.body?.sku, productDTO.sku)
        Assert.assertEquals(httpResponse.body?.description, productDTO.description)
        Assert.assertEquals(httpResponse.body?.price, productDTO.price)
        Assert.assertEquals(httpResponse.body?.discount, productDTO.discount)
        Assert.assertEquals(httpResponse.body?.availableQuantity, productDTO.availableQuantity)
        Assert.assertEquals(httpResponse.body?.pendingQuantity, productDTO.pendingQuantity)
        Assert.assertEquals(httpResponse.body?.completedQuantity, productDTO.completedQuantity)
    }

    @Test
    fun readResource() {
        /* Given */
        var productModel = ProductModel(
            name = "Steel",
            sku = "XXX111",
            description = "new product",
            price = 10000.00,
            discount = true,
            availableQuantity = 10,
            pendingQuantity = 0,
            completedQuantity = 0
        )
        var productDTO = ProductDTO(productModel)
        val serviceMock = mock<ProductServiceImpl> {
            on { getEntity(any()) } doReturn productModel
        }
        val controller = ProductController(serviceMock)

        /* When */
        val httpResponse: ResponseEntity<ProductDTO> = controller.readResource(productModel.id)

        /* Then */
        Assert.assertEquals(httpResponse.statusCode, HttpStatus.OK)
        Assert.assertEquals(httpResponse.body?.productId, productDTO.productId)
        Assert.assertEquals(httpResponse.body?.name, productDTO.name)
        Assert.assertEquals(httpResponse.body?.sku, productDTO.sku)
        Assert.assertEquals(httpResponse.body?.description, productDTO.description)
        Assert.assertEquals(httpResponse.body?.price, productDTO.price)
        Assert.assertEquals(httpResponse.body?.discount, productDTO.discount)
        Assert.assertEquals(httpResponse.body?.availableQuantity, productDTO.availableQuantity)
        Assert.assertEquals(httpResponse.body?.pendingQuantity, productDTO.pendingQuantity)
        Assert.assertEquals(httpResponse.body?.completedQuantity, productDTO.completedQuantity)
    }

    @Test
    fun readResources() {
        /* Given */
        var productModel = ProductModel(
            name = "Steel",
            sku = "XXX111",
            description = "new product",
            price = 10000.00,
            discount = true,
            availableQuantity = 10,
            pendingQuantity = 0,
            completedQuantity = 0
        )
        var productDTO = ProductDTO(productModel)
        val productsList = mutableListOf(productDTO)
        val serviceMock = mock<ProductServiceImpl> {
            on { getEntities() } doReturn listOf(productModel)
        }
        val controller = ProductController(serviceMock)

        /* When */
        val httpResponse: ResponseEntity<List<ProductDTO>> = controller.readResources()

        /* Then */
        Assert.assertEquals(httpResponse.statusCode, HttpStatus.OK)
        Assert.assertEquals(httpResponse.body?.get(0)?.productId, productsList[0].productId)
        Assert.assertEquals(httpResponse.body?.get(0)?.name, productsList[0].name)
        Assert.assertEquals(httpResponse.body?.get(0)?.sku, productsList[0].sku)
        Assert.assertEquals(httpResponse.body?.get(0)?.description, productsList[0].description)
        Assert.assertEquals(httpResponse.body?.get(0)?.price, productsList[0].price)
        Assert.assertEquals(httpResponse.body?.get(0)?.discount, productsList[0].discount)
        Assert.assertEquals(httpResponse.body?.get(0)?.availableQuantity, productsList[0].availableQuantity)
        Assert.assertEquals(httpResponse.body?.get(0)?.pendingQuantity, productsList[0].pendingQuantity)
        Assert.assertEquals(httpResponse.body?.get(0)?.completedQuantity, productsList[0].completedQuantity)
    }

    @Test
    fun updateProduct() {
        /* Given */
        var productModel = ProductModel(
            name = "Steel",
            sku = "XXX111",
            description = "new product",
            price = 10000.00,
            discount = true,
            availableQuantity = 10,
            pendingQuantity = 0,
            completedQuantity = 0
        )
        val serviceMock = mock<ProductServiceImpl> {
            on { updateEntity(any(), any()) } doReturn productModel
        }
        val controller = ProductController(serviceMock)

        /* When */
        val httpResponse: ResponseEntity<ResourceUpdatedDTO> = controller.updateResource(productModel.id, productModel)

        /* Then */
        Assert.assertEquals(httpResponse.statusCode, HttpStatus.OK)
        Assert.assertEquals(httpResponse.body?.id, productModel.id)
        Assert.assertEquals(httpResponse.body?.status, EntityStatusEnum.UPDATED)
    }
}