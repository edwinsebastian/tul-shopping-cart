package com.tul.shoppingcart.demo.model

import java.io.Serializable
import java.util.UUID
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class Model: Serializable {
    @Id
    @GeneratedValue
    var id: UUID = UUID.randomUUID()
}