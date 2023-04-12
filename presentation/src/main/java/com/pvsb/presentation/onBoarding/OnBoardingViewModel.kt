package com.pvsb.presentation.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.useCase.getUserData.GetUserDataUseCase
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoardingUseCase
import com.pvsb.presentation.onBoarding.onBoarding.OnBoardingScreenState
import com.pvsb.presentation.onBoarding.onBoarding.OnBoardingScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val skipOnBoardingUseCase: SkipOnBoardingUseCase,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnBoardingScreenState())
    val state = _state.asStateFlow()

    fun skipOnBoarding() {
        viewModelScope.launch {
            skipOnBoardingUseCase()
        }
    }

    fun resolveNextUsersDestinationFromSplash() {
        viewModelScope.launch {
            val state = getUserDataUseCase()

            if (state is DataState.Success && state.data.hasSeenOnBoardingAlready) {
                _state.update {
                    it.copy(nextDestination = OnBoardingScreens.PasswordScreen.Enter)
                }
            }

        }
    }
}