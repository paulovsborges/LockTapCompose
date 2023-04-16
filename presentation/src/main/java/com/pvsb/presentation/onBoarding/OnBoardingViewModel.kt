package com.pvsb.presentation.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.getUserData.GetUserDataUseCase
import com.pvsb.domain.useCase.login.Login
import com.pvsb.domain.useCase.login.LoginUseCase
import com.pvsb.domain.useCase.registerPassword.RegisterPasswordUseCase
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoardingUseCase
import com.pvsb.presentation.R
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
    private val getUserDataUseCase: GetUserDataUseCase,
    private val registerPasswordUseCase: RegisterPasswordUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnBoardingScreenState())
    val state = _state.asStateFlow()

    fun skipOnBoarding() {
        viewModelScope.launch {
            skipOnBoardingUseCase()
        }
    }

    fun registerNewPassword(
        newPassword: String
    ) {
        viewModelScope.launch {
            registerPasswordUseCase(newPassword)
        }
    }

    fun login(password: String) {
        viewModelScope.launch {

            when (val state = loginUseCase(password)) {
                is DataState.Error -> {
                    val error = when (state.error) {
                        Login.Error.INVALID_PASSWORD -> {
                            TypedMessage.Reference(R.string.password_incorrect_label)
                        }
                        else -> {
                            TypedMessage.Reference(R.string.error_there_was_an_unexpected_error)
                        }
                    }

                    setError(error)
                }
                is DataState.Success -> {
                    _state.update { it.copy(isAuthenticated = true) }
                }
            }
        }
    }

    fun resolveNextUsersDestinationFromSplash() {
        viewModelScope.launch {
            val state = getUserDataUseCase()

            if (state is DataState.Success) {

                val nextDestination = when {
                    state.data.password.isNotEmpty() -> {
                        OnBoardingScreens.PasswordScreen.Enter
                    }
                    state.data.hasSeenOnBoardingAlready -> {
                        OnBoardingScreens.PasswordScreen.Create
                    }
                    else -> {
                        OnBoardingScreens.OnBoarding
                    }
                }

                _state.update {
                    it.copy(nextDestination = nextDestination)
                }
            }
        }
    }

    fun setError(error: TypedMessage) {
        _state.update { it.copy(error = error) }
    }

    fun dismissError() {
        _state.update { it.copy(error = null) }
    }
}
