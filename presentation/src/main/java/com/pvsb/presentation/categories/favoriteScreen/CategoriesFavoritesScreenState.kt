package com.pvsb.presentation.categories.favoriteScreen

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.entity.TypedMessage

data class CategoriesFavoritesScreenState(
    val contacts: List<Contact> = emptyList(),
    val passwords: List<Password> = emptyList(),
    val photos: List<Photo> = emptyList(),
    val error: TypedMessage? = null
)
