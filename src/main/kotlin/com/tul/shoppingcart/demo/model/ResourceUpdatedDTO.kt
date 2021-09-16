package com.tul.shoppingcart.demo.model

import com.tul.shoppingcart.demo.enum.EntityStatusEnum
import java.util.UUID

data class ResourceUpdatedDTO(
    var id: UUID,
    var status: EntityStatusEnum
)