package com.pvsb.presentation.passwords.passwordsList

import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.TypedMessage

data class PasswordsListState(
    val passwords: List<Password> = emptyList(),
    val error: TypedMessage? = null
)
