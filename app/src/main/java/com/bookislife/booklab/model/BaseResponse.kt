package com.bookislife.booklab.model

data class BaseResponse<T>(val data: T, val code: Int = 0)