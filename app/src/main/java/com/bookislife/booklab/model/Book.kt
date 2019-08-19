package com.bookislife.booklab.model

import java.math.BigDecimal

data class Book(
    val id: Long? = null,
    val bookName: String,
    val author:String = "",
    val price: BigDecimal = BigDecimal.ZERO,
    val cover: String = "",
    val summary: String = ""
)