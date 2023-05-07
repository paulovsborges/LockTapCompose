package com.pvsb.presentation.passwords.categoriesPasswordsList

import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.TypedMessage

data class PasswordsListState(
    val allPasswords: List<Password> = emptyList(),
    val favoritePasswords: List<Password> = emptyList(),
    val error: TypedMessage? = null
)
