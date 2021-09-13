package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.model.ShoppingCartModel
import com.tul.shoppingcart.demo.service.ICrudService
import com.tul.shoppingcart.demo.service.IShoppingCartService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/shoppingcart")
class ShoppingCartController(
    private var iShoppingCartService: IShoppingCartService<ShoppingCartModel>,
    override var iCrudService: ICrudService<ShoppingCartModel>
): ICrudController<ShoppingCartModel>