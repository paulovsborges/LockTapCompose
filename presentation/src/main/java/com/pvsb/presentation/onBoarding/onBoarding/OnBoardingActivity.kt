package com.pvsb.presentation.onBoarding.onBoarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pvsb.presentation.ui.theme.LockTapComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
