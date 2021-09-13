package com.tul.shoppingcart.demo.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
abstract class Model {
    @Id
    @GeneratedValue
    var id: UUID = UUID.randomUUID()
}