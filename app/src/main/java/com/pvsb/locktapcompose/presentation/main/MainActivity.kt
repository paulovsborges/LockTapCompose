package com.pvsb.locktapcompose.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pvsb.locktapcompose.presentation.ui.theme.LockTapComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LockTapComposeTheme(darkTheme = true) {
                MainNavigation()
            }
        }
    }
}
