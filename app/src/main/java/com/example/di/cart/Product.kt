package com.example.di.cart

data class Product(
    var productId: Long?,
    var productNane: String?,
    var productQty: Double?,
    var productPrice: Double?,
    var total: Double? = (productPrice?.let { productQty?.times(it) })
) {}