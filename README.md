# tul-shopping-cart
Postman collection
https://www.getpostman.com/collections/1119b16fcb52d2142127

### crear shopping_cart POST /v1/shoppingcart
##### request body
```
{
    "status": "PENDING",
    "total": 0.0
}
```
##### response body
```
{
    "shoppingCartId": "71aa620c-5fce-4050-98cb-3a8e96510414",
    "products": [],
    "status": "PENDING",
    "total": 0.0
}
```

### agregar products al shopping_cart PUT /v1/shoppingcart/{shoppingCartId}/product
##### request body
```
{
    "productId": "dc112834-9969-46cc-9e4a-ac41923cb5c5",
    "quantity": 5
}
```
##### response body
```
{
    "shoppingCartId": "71aa620c-5fce-4050-98cb-3a8e96510414",
    "products": [
        {
            "productId": "dc112834-9969-46cc-9e4a-ac41923cb5c5",
            "quantity": 5,
            "unitPrice": 36112.14,
            "discountPrice": 18056.07
        }
    ],
    "status": "PENDING",
    "total": 288897.12
}
```
NOTA:
en el request body se debe mandar la cantidad final de product
si se requieren 5 ```"quantity": 5```

para aumentar la cantidad si ya se habian agregado 5 y se quiere agregar 2 mas ```"quantity": 7```

para disminuir la cantidad y ya se habian agregado 5 y se quiere quitar 1 ```"quantity": 4```

### agregar products al shopping_cart PUT /v1/shoppingcart/{shoppingCartId}/checkout
##### response body
```
{
    "shoppingCartId": "71aa620c-5fce-4050-98cb-3a8e96510414",
    "products": [
        {
            "productId": "dc112834-9969-46cc-9e4a-ac41923cb5c5",
            "quantity": 5,
            "unitPrice": 36112.14,
            "discountPrice": 18056.07
        }
    ],
    "status": "COMPLETED",
    "total": 288897.12
}
```




