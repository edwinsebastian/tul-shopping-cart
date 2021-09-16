package com.tul.shoppingcart.demo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadArgumentsException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
class MethodNotAllowedException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
class OutStockEx(message: String?): RuntimeException(message)