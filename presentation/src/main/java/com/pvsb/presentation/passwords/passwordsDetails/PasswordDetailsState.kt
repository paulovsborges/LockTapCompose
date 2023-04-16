package com.pvsb.presentation.passwords.passwordsDetails

import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.TypedMessage

data class PasswordDetailsState(
    val details: Password = Password(
        "",
        "",
        "",
        null,
        false,
        null
    ),
    val error: TypedMessage? = null
)
