package com.pvsb.presentation.passwords.passwordsDetails

import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.TypedMessage

data class PasswordDetailsState(
    val passwordDetails: PasswordDetails = PasswordDetails(),
    val fields: PasswordDetailsFields = PasswordDetailsFields(),
    val error: TypedMessage? = null,
    val shouldCloseScreen: Boolean = false,
    val isSaveButtonEnabled: Boolean = false
) {
    fun toggleButtonEnabled(): Boolean {

        val details = this.passwordDetails.details.copy(
            title = fields.title,
            password = fields.password,
            additionalInfo = fields.additionalInfo
        )

        return this.passwordDetails.details != details
    }
}

data class PasswordDetails(
    val details: Password = Password(
        "",
        "",
        "",
        null,
        false,
        ""
    )
)

data class PasswordDetailsFields(
    val title: String = "",
    val password: String = "",
    val additionalInfo: String = "",
)

