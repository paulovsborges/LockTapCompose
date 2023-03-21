package com.pvsb.locktapcompose.presentation.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.pvsb.locktapcompose.R

@Composable
fun MainContent() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Main screen")

        Image(
            painter = painterResource(id = R.drawable.bg_onboarding_step_1),
            contentDescription = ""
        )
    }
}