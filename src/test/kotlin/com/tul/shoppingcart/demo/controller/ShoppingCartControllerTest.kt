package com.tul.shoppingcart.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tul.shoppingcart.demo.enum.EntityStatusEnum
import com.tul.shoppingcart.demo.enum.ShoppingCartStatusEnum
import com.tul.shoppingcart.demo.exception.MethodNotAllowedException
import com.tul.shoppingcart.demo.exception.ResourceNotFoundException
import com.tul.shoppingcart.demo.model.*
import com.tul.shoppingcart.demo.service.*
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

internal class ShoppingCartControllerTest(){

    @Test
    fun createResource() {
        /* Given */
        val shoppingCartModel = ShoppingCartModel(
            shoppingCartProductsModel = mutableSetOf<ShoppingCartProductsModel>(),
            status = ShoppingCartStatusEnum.PENDING,
            totalCost = 1000.0
        )
        val shoppingCartDTO = ShoppingCartDTO(shoppingCartModel)
        val serviceMock = mock<ShoppingCartServiceImpl> {
            on { createEntity(any()) } doReturn shoppingCartModel
        }
        val controller = ShoppingCartController(mock<ShoppingCartProductServiceImpl>{}, serviceMock)

        /* When */
        val httpResponse: ResponseEntity<ShoppingCartDTO> = controller.createResource(shoppingCartModel)

        /* Then */
        Assert.assertEquals(httpResponse.statusCode, HttpStatus.OK)
        Assert.assertEquals(httpResponse.body?.shoppingCartId, shoppingCartDTO.shoppingCartId)
        Assert.assertEquals(httpResponse.body?.status, shoppingCartDTO.status)
        Assert.assertEquals(httpResponse.body?.products, shoppingCartDTO.products)
        Assert.assertEquals(httpResponse.body?.total, shoppingCartDTO.total)
    }

    @Test
    fun readResource() {
        /* Given */
        val shoppingCartModel = ShoppingCartModel(
            shoppingCartProductsModel = mutableSetOf<ShoppingCartProductsModel>(),
            status = ShoppingCartStatusEnum.PENDING,
            totalCost = 1000.0
        )
        val shoppingCartDTO = ShoppingCartDTO(shoppingCartModel)
        val serviceMock = mock<ShoppingCartServiceImpl> {
            on { getEntity(any()) } doReturn shoppingCartModel
        }
        val controller = ShoppingCartController(mock<ShoppingCartProductServiceImpl>{}, serviceMock)

        /* When */
        val httpResponse: ResponseEntity<ShoppingCartDTO> = controller.readResource(shoppingCartModel.id)

        /* Then */
        Assert.assertEquals(httpResponse.statusCode, HttpStatus.OK)
        Assert.assertEquals(httpResponse.body?.shoppingCartId, shoppingCartDTO.shoppingCartId)
        Assert.assertEquals(httpResponse.body?.status, shoppingCartDTO.status)
        Assert.assertEquals(httpResponse.body?.products, shoppingCartDTO.products)
        Assert.assertEquals(httpResponse.body?.total, shoppingCartDTO.total)
    }

    @Test
    fun readResources() {
        /* Given */
        val shoppingCartModel = ShoppingCartModel(
            shoppingCartProductsModel = mutableSetOf<ShoppingCartProductsModel>(),
            status = ShoppingCartStatusEnum.PENDING,
            totalCost = 0.0
        )
        val shoppingCartDTO = ShoppingCartDTO(shoppingCartModel)
        val shoppingCartsList: MutableList<ShoppingCartDTO> = mutableListOf(shoppingCartDTO)
        val serviceMock = mock<ShoppingCartServiceImpl> {
            on { getEntities() } doReturn listOf(shoppingCartModel)
        }
        val controller = ShoppingCartController(mock<ShoppingCartProductServiceImpl>{}, serviceMock)

        /* When */
        val httpResponse: ResponseEntity<List<ShoppingCartDTO>> = controller.readResources()

        /* Then */
        Assert.assertEquals(httpResponse.statusCode, HttpStatus.OK)
        Assert.assertEquals(httpResponse.body?.get(0)?.shoppingCartId, shoppingCartsList[0].shoppingCartId)
        Assert.assertEquals(httpResponse.body?.get(0)?.products, shoppingCartsList[0].products)
        Assert.assertEquals(httpResponse.body?.get(0)?.status, shoppingCartsList[0].status)
        Assert.assertEquals(httpResponse.body?.get(0)?.total, shoppingCartsList[0].total)
    }

    @Test
    fun updateResource() {
        /* Given */
        val shoppingCartModel = ShoppingCartModel(
            shoppingCartProductsModel = mutableSetOf<ShoppingCartProductsModel>(),
            status = ShoppingCartStatusEnum.PENDING,
            totalCost = 1000.0
        )
        val controller = ShoppingCartController(mock<ShoppingCartProductServiceImpl>{}, mock<ShoppingCartServiceImpl>{})
        var expectedEx: MethodNotAllowedException? = null

        /* When */
        try {
            controller.updateResource(shoppingCartModel.id, shoppingCartModel)
        }catch (ex: MethodNotAllowedException){
            expectedEx = ex
        }

        /* Then */
        Assert.assertNotNull(expectedEx)
        Assert.assertEquals(expectedEx?.message, "Is not allowed modify the shopping cart.")
    }

    @Test
    fun modifyCartItems() {
        /* Given */
        val shoppingCartModel = ShoppingCartModel(
            shoppingCartProductsModel = mutableSetOf<ShoppingCartProductsModel>(),
            status = ShoppingCartStatusEnum.PENDING,
            totalCost = 1000.0
        )
        val productModel = ProductModel(
            name = "Steel",
            sku = "XXX111",
            description = "new product",
            price = 10000.00,
            discount = true,
            availableQuantity = 10,
            pendingQuantity = 0,
            completedQuantity = 0
        )
        val productQuantityDTO = ProductQuantityDTO(
            productId = productModel.id,
            quantity = 5,
            unitPrice = productModel.price,
            discountPrice = productModel.finalPrice
        )
        val shoppingCartDTO = ShoppingCartDTO(shoppingCartModel).apply {
            products = listOf(productQuantityDTO)
        }
        val cartServiceMock = mock<ShoppingCartProductServiceImpl>{
            on { modifyCartItems(any(), any(), any()) } doReturn shoppingCartDTO
        }
        val controller = ShoppingCartController(cartServiceMock, mock<ShoppingCartServiceImpl>{})

        /* When */
        val httpResponse: ResponseEntity<ShoppingCartDTO> = controller.modifyCartItems(shoppingCartModel.id, productQuantityDTO)

        /* Then */
        Assert.assertEquals(httpResponse.statusCode, HttpStatus.OK)
        Assert.assertEquals(httpResponse.body?.shoppingCartId, shoppingCartModel.id)
        Assert.assertEquals(httpResponse.body?.status, ShoppingCartStatusEnum.PENDING)
        Assert.assertEquals(httpResponse.body?.products?.get(0)?.productId, productQuantityDTO.productId)
        Assert.assertEquals(httpResponse.body?.products?.get(0)?.quantity, productQuantityDTO.quantity)
        Assert.assertEquals(httpResponse.body?.products?.get(0)?.unitPrice, productQuantityDTO.unitPrice)
        Assert.assertEquals(httpResponse.body?.products?.get(0)?.discountPrice, productQuantityDTO.discountPrice)
    }

    @Test
    fun shoppingCartCheckout() {
        /* Given */
        val shoppingCartModel = ShoppingCartModel(
            shoppingCartProductsModel = mutableSetOf<ShoppingCartProductsModel>(),
            status = ShoppingCartStatusEnum.PENDING,
            totalCost = 1000.0
        )
        val productQuantityDTO = ProductQuantityDTO(
            productId = UUID.randomUUID(),
            quantity = 10,
            unitPrice = 1500.0,
            discountPrice = 750.0
        )
        val shoppingCartDTO = ShoppingCartDTO(shoppingCartModel).apply {
            products = listOf(productQuantityDTO)
        }
        val cartServiceMock = mock<ShoppingCartProductServiceImpl>{
            on { checkout(any()) } doReturn shoppingCartDTO
        }
        val controller = ShoppingCartController(cartServiceMock, mock<ShoppingCartServiceImpl>{})

        /* When */
        val httpResponse: ResponseEntity<ShoppingCartDTO> = controller.shoppingCartCheckout(shoppingCartModel.id)

        /* Then */
        Assert.assertEquals(httpResponse.statusCode, HttpStatus.OK)
        Assert.assertEquals(httpResponse.body?.shoppingCartId, shoppingCartModel.id)
        Assert.assertEquals(httpResponse.body?.status, ShoppingCartStatusEnum.PENDING)
        Assert.assertEquals(httpResponse.body?.products?.get(0)?.productId, productQuantityDTO.productId)
        Assert.assertEquals(httpResponse.body?.products?.get(0)?.quantity, productQuantityDTO.quantity)
        Assert.assertEquals(httpResponse.body?.products?.get(0)?.unitPrice, productQuantityDTO.unitPrice)
        Assert.assertEquals(httpResponse.body?.products?.get(0)?.discountPrice, productQuantityDTO.discountPrice)
    }
}