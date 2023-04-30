package com.pvsb.presentation.categories.favoriteScreen

import com.pvsb.domain.entity.Contact

data class CategoriesFavoritesScreenState(
    val contacts: List<Contact> = emptyList()
)