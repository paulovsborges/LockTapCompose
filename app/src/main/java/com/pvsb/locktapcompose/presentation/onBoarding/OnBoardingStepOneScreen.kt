package com.pvsb.locktapcompose.presentation.onBoarding

import androidx.compose.runtime.Composable
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.onBoarding.shared.ComposeOnBoardingPrintsBackgroundBuilder

@Composable
fun OnBoardingStepOneScreen() {

    val drawables = listOf(
        R.drawable.on_boarding_step_1_1,
        R.drawable.on_boarding_step_1_2,
        R.drawable.on_boarding_step_1_3,
        R.drawable.on_boarding_step_1_4,
    )

    ComposeOnBoardingPrintsBackgroundBuilder(drawables)
}