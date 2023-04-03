package com.pvsb.domain.entity

sealed interface TypedMessage {

    data class StringMessage(
        val message: String
    ) : TypedMessage

    data class Reference(
        val resId: Int
    ) : TypedMessage
}
