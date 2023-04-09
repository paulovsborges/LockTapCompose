package com.pvsb.domain.entity

data class Contact(
    val contactId: String,
    val name: String,
    val phoneNumber: String,
    val imageFilePath: String?,
    val isFavorite: Boolean
)
