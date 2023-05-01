package com.pvsb.presentation.categories.favoriteScreen

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.Password

data class CategoriesFavoritesScreenState(
    val contacts: List<Contact> = emptyList(),
    val passwords: List<Password> = emptyList()
)
