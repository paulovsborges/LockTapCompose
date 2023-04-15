package com.pvsb.presentation.onBoarding.onBoarding

import com.pvsb.domain.entity.TypedMessage

data class OnBoardingScreenState(
    val nextDestination: OnBoardingScreens = OnBoardingScreens.OnBoarding,
    val error: TypedMessage? = null
)