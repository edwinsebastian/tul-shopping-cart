package com.tul.shoppingcart.demo.model

import java.util.UUID
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class Model {
    @Id
    @GeneratedValue
    var id: UUID = UUID.randomUUID()
}