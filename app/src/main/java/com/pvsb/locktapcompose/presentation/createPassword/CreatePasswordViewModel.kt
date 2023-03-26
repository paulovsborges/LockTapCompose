package com.pvsb.locktapcompose.presentation.createPassword

import androidx.lifecycle.ViewModel

class CreatePasswordViewModel : ViewModel() {

    private var password = ""

    fun setPassword(newPassword: String) {
        password = newPassword
    }

    fun getPassword(): String {
        return password
    }
}