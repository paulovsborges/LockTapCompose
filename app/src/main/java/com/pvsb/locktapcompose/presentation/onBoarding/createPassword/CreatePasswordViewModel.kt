package com.pvsb.locktapcompose.presentation.onBoarding.createPassword

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