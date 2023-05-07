package com.pvsb.domain.util

interface Logger {
    fun d(event: String)
    fun e(exception: Exception)
}