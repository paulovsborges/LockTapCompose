package com.pvsb.presentation.onBoarding.onBoarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pvsb.presentation.R

data class OnBoardingStepsData(
    val drawables: List<Int>,
    val title: String,
    val message: String
)

@Composable
fun getOnBoardingData(): List<OnBoardingStepsData> {
    return listOf(
        OnBoardingStepsData(
            listOf(
                R.drawable.on_boarding_step_1_1,
                R.drawable.on_boarding_step_1_2,
                R.drawable.on_boarding_step_1_3,
                R.drawable.on_boarding_step_1_4,
            ),
            stringResource(id = R.string.on_boarding_step_1_title),
            stringResource(id = R.string.on_boarding_step_1_message),
        ),
        OnBoardingStepsData(
            listOf(
                R.drawable.on_boarding_step_2_1,
                R.drawable.on_boarding_step_2_2,
                R.drawable.on_boarding_step_2_3,
                R.drawable.on_boarding_step_2_1,
            ),
            stringResource(id = R.string.on_boarding_step_2_title),
            stringResource(id = R.string.on_boarding_step_2_message),
        ),
        OnBoardingStepsData(
            listOf(
                R.drawable.on_boarding_step_3_1,
                R.drawable.on_boarding_step_3_2,
                R.drawable.on_boarding_step_3_3,
                R.drawable.on_boarding_step_2_3,
            ),
            stringResource(id = R.string.on_boarding_step_3_title),
            stringResource(id = R.string.on_boarding_step_3_message),
        )
    )
}
