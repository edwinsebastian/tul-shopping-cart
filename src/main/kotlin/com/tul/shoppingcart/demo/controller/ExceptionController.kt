package com.tul.shoppingcart.demo.controller

import com.tul.shoppingcart.demo.exception.BadArgumentsException
import com.tul.shoppingcart.demo.exception.InternalException

import com.tul.shoppingcart.demo.exception.ResourceNotFoundException

import org.springframework.web.bind.annotation.PathVariable

import org.springframework.web.bind.annotation.GetMapping

class ExceptionController {
    @GetMapping("/exception/{exception_id}")
    fun getSpecificException(@PathVariable("exception_id") pException: String) {
        if ("not_found" == pException) {
            throw ResourceNotFoundException("resource not found")
        } else if ("bad_arguments" == pException) {
            throw BadArgumentsException("bad arguments")
        } else {
            throw InternalException("internal error")
        }
    }
}

