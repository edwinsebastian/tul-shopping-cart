package com.tul.shoppingcart.demo.model

import com.tul.shoppingcart.demo.enum.ShoppingCartStatusEnum
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Enumerated
import javax.persistence.EnumType

@Entity
class ShoppingCartModel(
    @OneToMany(mappedBy = "shoppingCartModel")
    var shoppingCartProductsModel: MutableSet<ShoppingCartProductsModel> = mutableSetOf(),
    @Enumerated(EnumType.STRING)
    var status: ShoppingCartStatusEnum = ShoppingCartStatusEnum.PENDING,
    var totalCost: Double = 0.0
):Model()