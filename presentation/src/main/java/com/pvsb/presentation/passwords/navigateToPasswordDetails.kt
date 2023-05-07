package com.pvsb.presentation.passwords

import android.content.Context
import android.content.Intent
import com.pvsb.presentation.passwords.passwordsDetails.PasswordDetailsActivity

fun Context.navigateToPasswordDetails(passwordId: String? = null) {
    val intent = Intent(this, PasswordDetailsActivity::class.java)

    passwordId?.let {
        intent.putExtra(PasswordDetailsActivity.PASSWORD_ID_KEY, passwordId)
    }

    startActivity(intent)

}