package com.pvsb.domain.entity

sealed class DataState {

    object Initial : DataState()

    data class Success<T>(
        val data: T
    ) : DataState()

    data class Error(
        val typedMessage: TypedMessage
    ) : DataState()
}
