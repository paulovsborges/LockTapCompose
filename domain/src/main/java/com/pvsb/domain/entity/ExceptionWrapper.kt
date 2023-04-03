package com.pvsb.domain.entity

sealed interface ExceptionWrapper {

    data class ContactAlreadyExists(
        override val message: String?
    ) : ExceptionWrapper, Exception()

}
