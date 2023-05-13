package com.pvsb.domain.entity

import java.util.Date

data class Memo(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Date?,
    val isFavorite: Boolean,
)
