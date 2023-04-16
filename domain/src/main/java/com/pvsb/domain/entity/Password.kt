package com.pvsb.domain.entity

import java.util.Date

data class Password(
    val id: String,
    val title: String,
    val password: String,
    val createdAt: Date?,
    val isFavorite: Boolean,
    val additionalInfo: String?
)
