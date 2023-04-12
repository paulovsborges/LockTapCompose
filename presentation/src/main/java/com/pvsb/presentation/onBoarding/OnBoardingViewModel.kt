package com.pvsb.presentation.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoardingUseCase
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val skipOnBoardingUseCase: SkipOnBoardingUseCase
): ViewModel() {

    fun skipOnBoarding(){
        viewModelScope.launch {
            skipOnBoardingUseCase()
        }
    }
}