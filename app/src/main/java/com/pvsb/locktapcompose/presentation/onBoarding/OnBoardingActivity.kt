package com.pvsb.locktapcompose.presentation.onBoarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pvsb.locktapcompose.presentation.OnBoardingNavigation
import com.pvsb.locktapcompose.presentation.ui.theme.LockTapComposeTheme

class OnBoardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LockTapComposeTheme(darkTheme = true) {
                OnBoardingNavigation()
            }
        }
    }
}
