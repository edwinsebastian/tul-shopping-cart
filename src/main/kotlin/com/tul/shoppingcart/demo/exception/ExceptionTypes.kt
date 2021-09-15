package com.tul.shoppingcart.demo.exception

import java.lang.RuntimeException

class OutStockEx(
    override var message: String
): RuntimeException()

class ResourceNotFoundEx(
    override var message: String
): RuntimeException()