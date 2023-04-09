package com.pvsb.domain.entity

sealed interface DataState<T> {

    data class Success<T>(
        val data: T
    ) : DataState<T>

    data class Error<T>(
        val error: ExceptionWrapper
    ) : DataState<T>
}
