package com.pvsb.presentation.onBoarding.createPassword

import com.pvsb.presentation.R

sealed class PasswordScreenType(
    val title: Int,
    val message: Int
) {

    object CreatePassword : PasswordScreenType(
        R.string.create_password_title,
        R.string.create_password_message,
    )

    data class RepeatPassword(
        val createdPassword: String
    ) : PasswordScreenType(
        R.string.repeat_password_title,
        R.string.repeat_password_message,
    )

    object EnterPassword : PasswordScreenType(
        R.string.enter_password_title,
        R.string.enter_password_message,
    )
}
