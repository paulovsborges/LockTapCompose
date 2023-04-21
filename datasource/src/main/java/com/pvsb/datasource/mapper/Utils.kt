package com.pvsb.datasource.mapper

@Suppress("UNCHECKED_CAST")
inline fun <reified T, R> isFavorite(code: T): R {

    return when (T::class) {
        Boolean::class -> {
            val value = if (code as Boolean) {
                1L
            } else {
                0L
            }

            value as R
        }
        Long::class -> {
            val value = (code as Long) == 1L

            value as R
        }
        else -> error("Type ${T::class} unexpected to convert on ")
    }
}