package com.pvsb.presentation.passwords.passwordsDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.password.getPassword.GetPasswordUseCase
import com.pvsb.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordDetailsViewModel @Inject constructor(
    private val getPassword: GetPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PasswordDetailsState())
    val state = _state.asStateFlow()

    fun getPasswordDetails(passwordId: String) {
        viewModelScope.launch {
            when (val state = getPassword(passwordId)) {
                is DataState.Error -> {
                    setError(TypedMessage.Reference(R.string.error_there_was_an_unexpected_error))
                }
                is DataState.Success -> {
                    _state.update { it.copy(details = state.data) }
                }
            }
        }
    }

    private fun setError(error: TypedMessage) {
        _state.update { it.copy(error = error) }
    }
}