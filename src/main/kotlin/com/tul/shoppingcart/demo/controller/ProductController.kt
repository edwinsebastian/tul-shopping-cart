package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.model.ProductModel
import com.tul.shoppingcart.demo.service.ICrudService
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("v1/products")
class ProductController(
    override var iCrudService: ICrudService<ProductModel>
): ICrudController<ProductModel>{}