package com.pvsb.locktapcompose.presentation.createPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.ui.messageTextStyle
import com.pvsb.locktapcompose.presentation.ui.titleTextStyle

@Composable
fun CreatePasswordScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bg_splash)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(65.dp))

        Text(
            text = stringResource(id = R.string.create_password_title),
            style = titleTextStyle
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(id = R.string.create_password_message),
            style = messageTextStyle
        )
    }
}