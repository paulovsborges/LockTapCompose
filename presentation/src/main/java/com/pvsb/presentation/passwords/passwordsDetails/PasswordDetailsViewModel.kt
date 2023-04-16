package com.pvsb.presentation.passwords.passwordsDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.password.addPassword.AddPasswordUseCase
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
    private val getPassword: GetPasswordUseCase,
    private val addPasswordUseCase: AddPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PasswordDetailsState())
    val state = _state.asStateFlow()

    fun addPassword(input: AddPasswordUseCase.Input) {
        viewModelScope.launch {
            when (addPasswordUseCase(input)) {
                is DataState.Error -> {
                    setError(TypedMessage.Reference(R.string.error_there_was_an_unexpected_error))
                }
                is DataState.Success -> {
                    _state.update { it.copy(shouldCloseScreen = true) }
                }
            }
        }
    }

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

    fun onFieldsChanged(
        newData: Password
    ) {
        _state.update {
            it.copy(
                details = newData,
                isSaveButtonEnabled = it.toggleButtonEnabled(newData)
            )
        }
    }

    private fun setError(error: TypedMessage) {
        _state.update { it.copy(error = error) }
    }

    fun dismissError() {
        _state.update { it.copy(error = null) }
    }
}